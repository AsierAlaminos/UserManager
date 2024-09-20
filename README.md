# UserManager
## API Documentation

### **Obtener todos los usuarios**

<details>
 <summary><code>GET</code> <code><b>/users/all</b></code> <code>(Obtiene la lista de todos los usuarios)</code></summary>

##### Responses

> | http code     | content-type                      | response                                                                   |
> |---------------|-----------------------------------|----------------------------------------------------------------------------|
> | `200`         | `application/json`                | `[{ "id": 1, "name": "John Doe", "mail": "john@example.com" }, ...]`    |
> | `404`         | `application/json`                | `[]` (sin usuarios)                                                         |
> | `500`         | `application/json`                | `{"error": "SQLException message"}`                                       |

##### Example cURL

> ```bash
> curl -X GET -H "Content-Type: application/json" http://localhost:8889/users/all
> ```

</details>

------------------------------------------------------------------------------------------

### **Obtener un usuario específico**

<details>
 <summary><code>GET</code> <code><b>/users</b></code> <code>(Obtiene un usuario específico por ID, nombre o correo)</code></summary>

##### Parameters

> - `id` (opcional): ID del usuario.
> - `name` (opcional): Nombre del usuario.
> - `mail` (opcional): Correo del usuario.

##### Responses

> | http code     | content-type                      | response                                                                   |
> |---------------|-----------------------------------|----------------------------------------------------------------------------|
> | `200`         | `application/json`                | `{"id": 1, "name": "John Doe", "mail": "john@example.com"}`             |
> | `400`         | `application/json`                | `{"error": "Bad Request"}`                                                |
> | `404`         | `application/json`                | `{"error": "Not Found"}`                                                  |
> | `500`         | `application/json`                | `{"error": "SQLException message"}`                                       |

##### Example cURL

> ```bash
> curl -X GET -H "Content-Type: application/json" "http://localhost:8889/users?id=1"
> ```

</details>

------------------------------------------------------------------------------------------

### **Crear un nuevo usuario**

<details>
 <summary><code>POST</code> <code><b>/users</b></code> <code>(Crea un nuevo usuario)</code></summary>

##### Request Body

> ```json
> {
>   "name": "John Doe",
>   "mail": "john@example.com"
> }
> ```

##### Responses

> | http code     | content-type                      | response                                                                   |
> |---------------|-----------------------------------|----------------------------------------------------------------------------|
> | `200`         | `application/json`                | `{"success": "User created"}`                                             |
> | `400`         | `application/json`                | `{"error": "Bad Request"}`                                                |
> | `500`         | `application/json`                | `{"error": "SQLException message"}`                                       |

##### Example cURL

> ```bash
> curl -X POST -H "Content-Type: application/json" -d '{"name": "John Doe", "mail": "john@example.com"}' http://localhost:8889/users
> ```

</details>

------------------------------------------------------------------------------------------

### **Actualizar un usuario existente**

<details>
 <summary><code>PUT</code> <code><b>/users/{id}</b></code> <code>(Actualiza un usuario existente por ID)</code></summary>

##### Parameters

> - `id`: ID del usuario a actualizar.

##### Request Body

> ```json
> {
>   "name": "Jane Doe",
>   "mail": "jane@example.com"
> }
> ```

##### Responses

> | http code     | content-type                      | response                                                                   |
> |---------------|-----------------------------------|----------------------------------------------------------------------------|
> | `200`         | `application/json`                | `{"success": "User updated"}`                                            |
> | `400`         | `application/json`                | `{"error": "Bad Request"}`                                                |
> | `500`         | `application/json`                | `{"error": "SQLException message"}`                                       |

##### Example cURL

> ```bash
> curl -X PUT -H "Content-Type: application/json" -d '{"name": "Jane Doe", "mail": "jane@example.com"}' http://localhost:8889/users/1
> ```

</details>

------------------------------------------------------------------------------------------

### **Eliminar un usuario**

<details>
 <summary><code>DELETE</code> <code><b>/users/{id}</b></code> <code>(Elimina un usuario por ID)</code></summary>

##### Parameters

> - `id`: ID del usuario a eliminar.

##### Responses

> | http code     | content-type                      | response                                                                   |
> |---------------|-----------------------------------|----------------------------------------------------------------------------|
> | `200`         | `application/json`                | `{"success": "User deleted"}`                                            |
> | `400`         | `application/json`                | `{"error": "Bad Request"}`                                                |
> | `500`         | `application/json`                | `{"error": "SQLException message"}`                                       |

##### Example cURL

> ```bash
> curl -X DELETE -H "Content-Type: application/json" http://localhost:8889/users/1
> ```

</details>
