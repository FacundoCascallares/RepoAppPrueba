package com.example.traveldreamsapp.models;

import com.google.gson.annotations.SerializedName;

public class UserProfileResponse {

    @SerializedName("nombre_usuario")
    private String name;

    @SerializedName("apellido_usuario")
    private String surname;

    @SerializedName("mail")
    private String email;

    @SerializedName("telefono")
    private String phone;

    // Constructor vacío (importante para librerías como Gson)
    public UserProfileResponse() {
    }

    // Getters y setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
