package com.domain.usuario.usecase

import com.data.usuario.persistence.repository.PersistenceUsuarioRepository
import com.domain.usuario.models.*
import com.domain.usuario.mapping.toUsuario
import com.domain.usuario.models.Usuario
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ProviderUseCase {
    private val repository = PersistenceUsuarioRepository()
    val logger: Logger = LoggerFactory.getLogger("EmployeeUseCaseLogger")

    //Aquí tengo todos los casos de uso.
    private val getAllUsuariosUseCase = GetAllUsuarioUseCase(repository)
    private val getEmployeByDniUseCase = GetUsuarioByDniUseCase(repository)
    private val updateUsuarioUseCase = UpdateUsuarioUseCase(repository)
    private val insertEmployeeUseCase = InsertUsuarioUseCase(repository)
    private val deleteEmployeUseCase = DeleteUsuarioUseCase(repository)
    private val loginUseCase = LoginUseCase(repository)
    private val registerUseCase = RegisterUseCase(repository)



    suspend fun getAllUsuarios() = getAllUsuariosUseCase()  //Lo invoco, como si fuera una función.


    suspend fun getUsuarioByDni(dni : String) : Usuario? {
        if (dni.isNullOrBlank()){
            logger.warn("El dni está vacío. No podemos buscar un usuario")
            return null
        }
        getEmployeByDniUseCase.dni = dni
        val emp = getEmployeByDniUseCase()
        return if (emp == null) {
            logger.warn("No se ha encontrado un empleado con ese $dni.")
            null
        }else{
            emp
        }
    }



    suspend fun insertEmployee(employee: Usuario?) : Boolean{
        if (employee == null){
            logger.warn( "No existen datos del empleado a insertar")
            return false
        }
        insertEmployeeUseCase.usuario = employee
        val res = insertEmployeeUseCase()
            return if (!res){
            logger.warn("No se ha insertado el empleado. Posiblemente ya exista")
            false
        }else{
            true
        }
    }

    suspend fun updateUsuario(updateUsuario: UpdateUsuario?, dni : String) : Boolean{
        if (updateUsuario == null){
            logger.warn("No existen datos del empleado a actualizar")
            return false
        }

        updateUsuarioUseCase.updateUsuario = updateUsuario
        updateUsuarioUseCase.dni = dni
        return updateUsuarioUseCase()
    }

    suspend fun deleteEmployee(dni : String) : Boolean{
        deleteEmployeUseCase.dni = dni
        return deleteEmployeUseCase()
    }

    suspend fun login(dni: String?, pass: String?): Usuario?  = loginUseCase(dni, pass)

    suspend fun register(usuario : UpdateUsuario): Usuario? {

        return if(
            usuario.dni.isNullOrBlank() ||
            usuario.name.isNullOrBlank() ||
            usuario.password.isNullOrBlank()
        )
            null
        else
            registerUseCase(usuario)

    }
}