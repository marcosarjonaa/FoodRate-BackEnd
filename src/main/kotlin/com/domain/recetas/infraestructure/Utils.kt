package com.domain.recetas.infraestructure

import com.ktor.ApplicationContext
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO

class Utils {
    companion object  {
        fun createBase64ToImg(img: String, idReceta: String): String?{
            val groupExtension = listOf("jpg", "jpeg", "png")

            val regex = "data:(image/[^;]+);base64,(.+)".toRegex()
            val resultado = regex.find(img)

            if(resultado!=null){
                val type= resultado.groupValues[1]
                var extension : String = type.split("/")[1]
                val body = resultado.groupValues[2]
                if (extension !in groupExtension){
                    return null
                }
                try {
                    if (extension == "jpg"){
                        extension = "jpeg"
                    }
                    val imgBytes = Base64.getDecoder().decode(body)
                    val inputStream = ByteArrayInputStream(imgBytes)
                    val bufferImage : BufferedImage = ImageIO.read(inputStream)
                    val path = "Upload/images/$idReceta"
                    val dir = File(path)
                    if (!dir.exists()){
                        dir.mkdirs()
                    }
                    if (dir.isDirectory){
                        val nameFile = idReceta+"_${SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())}.$extension"
                        val fileImag = File("$path/$nameFile")
                        ImageIO.write(bufferImage, extension, fileImag)
                        return nameFile
                    }else {
                        return null
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    return null
                }
            }else {
                return null
            }
        }

        fun deleteImage(idRecetas: String, img: String): Boolean{
            try {
                val path = "${ApplicationContext.context.environment.config.property("ktor.path.images").getString()}/$idRecetas"
                val img = File(path, img)
                return if (img.exists()){
                    img.delete()
                    true
                }else {
                    false
                }
            }catch (e: Exception){
                e.printStackTrace()
                return false
            }
        }

        fun createDir(dni: String): Boolean{
            try {
                val path = ApplicationContext.context.environment.config.property("ktor.path.images").getString()
                val dir = File(path, dni)
                if (!dir.exists()){
                    val created = dir.mkdirs()
                    if (created){
                        return true
                    }else {
                        return false
                    }
                }else {
                    return false
                }
            }catch (e: Exception){
                e.printStackTrace()
                return false
            }

        }

        fun deleteDirectory(idReceta: String): Boolean{
            try {
                val path = ApplicationContext.context.environment.config.property("ktor.path.images").getString()+"/$idReceta"
                val dir = File(path)
                if (dir.exists()){
                    return dir.deleteRecursively()
                }
            }catch (e: Exception){
                e.printStackTrace()
                return false
            }
            return true
        }
    }
}