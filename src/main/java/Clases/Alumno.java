package Clases;

import Estructuras.ArregloDinamico;

public class Alumno implements Comparable<Alumno>{
    private String matricula;
    private String nombre;
    private String telefono;
    private String email;
    
    //atributos de domicilio
    private String calle;
    private String numero;
    private String colonia;
    private String ciudad;
    
    ArregloDinamico<Double> calificaciones;

    public Alumno() {
    }

    //con todo pero sin parametro calificaciones
    public Alumno(String matricula, String nombre, String telefono, String email, String calle, String numero, String colonia, String ciudad) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.calificaciones = new ArregloDinamico<>();
    }
    
    //con todo
    public Alumno(String matricula, String nombre, String telefono, String email, String calle, String numero, String colonia, String ciudad, ArregloDinamico<Double> calificaciones) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.calificaciones = calificaciones;
    }

    public void agregarCalificacion(double calif) {
        calificaciones.agregar(calif);
    }

    //metodo para calcular el promedio de manera recursiva
    public double getPromedioRecursivo() {
        if (calificaciones.size() == 0) return 0.0;
        return sumaRecursiva(0) / calificaciones.size();
    }
    //metodo usado en getPromedioRecursivo
    private double sumaRecursiva(int indice) {
        if (indice == calificaciones.size()) return 0.0;
        return calificaciones.get(indice) + sumaRecursiva(indice + 1);
    }
    
    public String getMatricula() {
        return matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    

    public ArregloDinamico<Double> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(ArregloDinamico<Double> calificaciones) {
        this.calificaciones = calificaciones;
    }

    @Override
    public int compareTo(Alumno o) {
        return this.matricula.compareToIgnoreCase(o.matricula);
    }
    
    //metodo que construye un string para ver la info del alumno, para cumplir con el punto 2
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----Informacion del alumno----\n");
        sb.append("Matrícula:  ").append(matricula).append("\n");
        sb.append("Nombre:     ").append(nombre).append("\n");
        sb.append("Teléfono:   ").append(telefono).append("\n");
        sb.append("Email:      ").append(email).append("\n");
        sb.append("Domicilio:  ").append(calle).append(" #").append(numero).append(", ").append(colonia).append(", ").append(ciudad).append("\n");
        
        sb.append("Calificaciones: ");
        if (calificaciones.size() == 0) {
         sb.append("Sin calificaciones registradas.\n");
        } else {
            for (int i = 0; i < calificaciones.size(); i++) {
                sb.append(calificaciones.get(i));
                if (i < calificaciones.size() - 1) sb.append(", ");
            }
            sb.append("\n");
            sb.append("Promedio:   ").append(getPromedioRecursivo()).append("\n");
        }
        return sb.toString();
    }
}
