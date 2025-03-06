-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS dbUsuario;

-- Crear la tabla usuario
CREATE TABLE IF NOT EXISTS Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(255),
    password VARCHAR(255),
    nombre VARCHAR(100),
    token VARCHAR(255)
);

-- Crear la tabla Recetas
CREATE TABLE IF NOT EXISTS Receta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    idReceta INT UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    image VARCHAR(1000) NOT NULL,
    nota VARCHAR(10) NOT NULL
);

-- Insertar datos en la tabla Recetas
INSERT INTO Receta (id, idReceta, name, description, image, nota) VALUES
(1, 1, 'Bolos lucentinos', 'Los bolos lucentinos son una comida que ha pasado generación tras generacion de abuelas a madres. Típica de Lucena, es un plato que une la carne y las almendras.', 'https://cocinandoentreolivos.com/wp-content/uploads/2017/11/Bolos-Lucentinos-tradicionales-Lucena-www.cocinandoentreolivos.com-30.jpg', '8'),
(2, 2, 'Tirabuzones', 'Los tirabuzones son una comida tipica de la semana santa, sobre todo en lucena donde cada vez desgraciadamente es menos visto', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPvpP7YSyRXRzS0sCiOwsKZqQBVcM9xbiNrg&s.jpg', '7'),
(3, 3, 'Flamenquines', 'Los flamenquínes son una comida típica cordobesa que se ha extendido por toda España. Aunque la receta original es lomo y jamon, se han encontrado variaciones de carne, condimentos por doquier', 'https://4.bp.blogspot.com/-M1tvwbGwXdc/WN4CDjc3hbI/AAAAAAAAOi4/8q2rSjYzrGgzrLXOy8enRIvNxsZ75FaOwCLcB/s1600/flacor.jpg', '9'),
(4, 4, 'Salmorejo', 'El salmorejo es una comida típica cordobesa cuya base es una mezcla de tomate junto a otras verduras. Además, también se le echan condimentos como huevo duro, jamon o picatostes', 'https://www.196flavors.com/wp-content/uploads/2021/09/Salmorejo-2fp.jpg', '7'),
(5, 5, 'Ochios', 'Los ochios son un plato común de la provincia de Jaén, originario de Úbeda y Baeza. Suelen comerse con morcilla en su mayoría, pero también hay variantes dulces y no saladas','https://www.aceitesdeolivadeespana.com/wp-content/uploads/2019/05/pan-de-ochios.jpg', '8'),
(6, 6, 'Spaghetti Carbonara', 'Receta italiana que se ha expandido por todo el mundo, sus ingredientes consisten en la propia pasta, guanciale, peccorino, pimienta negra y yema de huevo. El mote de la carbonara viene debido a los pueblos italianos','https://i.blogs.es/e0be61/salsa_carbonara/1366_2000.jpg', '10'),
(7, 7, 'Pizza margharitta', 'El nombre margharitta viene debido a la reina italiana a napoles, donde ,buscando honorificar la bandera italiana, ', 'https://www.annarecetasfaciles.com/files/pizza-margarita-1-scaled.jpg', '8'),
(8, 8, 'Tortilla de patatas', 'La tortilla de patatas es una receta con origen español que mezcla las patatas con el huevo, aunque existe el debate si va acompañada de cebolla o no', 'https://www.romagnolipatate.it/images/shutterstock_1669555969-2-2-mi.jpg', '10'),
(9, 9, 'Patatas bravas', 'Las patatas bravas tienen su origen en Madrid, aunque no se sabe de forma certera donde se vendieron las primeras. Es una tapa típica de España. Donde sobre todo en bares, se suele pedir para comer', 'https://www.lieferando.de/foodwiki/uploads/sites/8/2018/01/patatas-bravas-2-1080x960.jpg', '7'),
(10, 10, 'Rabo de toro', 'El rabo de toro es una comida típica cordobesa aunque cada vez más extendida por España. Esta comida suele servirse sobre un caldo de verduras, donde resalta la zanahoria y acompañado de un parmentier de patata', 'https://cdn.tasteatlas.com/images/dishes/5d1064ee4af349a4b9a72993acc6e993.jpg', '7');

-- Insertar datos en la tabla usuario (corregir la duplicación de "password")
INSERT INTO Usuario (id, dni, email, password, nombre, token) VALUES
(1, '50643065D', 'marcosarjonacomino@gmail.com', '$2a$10$9BGoytMKFORGoVvep1RTF.zek2M8VonYGscILJGFRwhr4WPA8O3.K', 'marcos', ''),
(2, '51218967Z', 'laurajimu05@gmail.com', '$2a$10$gTqOWD9KU0Im2/fOMKjqRO6qtc8rxBZMVzK4sCCWJNmeP0AjyF7eO', 'laura', '');