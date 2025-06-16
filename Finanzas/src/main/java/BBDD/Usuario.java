package BBDD;

import jakarta.persistence.*;
import org.hibernate.Length;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    public String nombre;

    @Column(name = "email", nullable = false, length = 100)
    public String email;

    @Column(name = "password", nullable = false)
    public String password;

    @OneToMany(mappedBy = "usuario")
    public Set<Categoria> categorias = new LinkedHashSet<>();



    @Column(name = "monto", nullable = false)
    private Integer monto;

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

}