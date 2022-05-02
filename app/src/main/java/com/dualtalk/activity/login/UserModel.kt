package com.dualtalk.activity.login

class UserModel(email: String, password: String, name: String) {
    var email: String
    var password: String
    var name: String

    constructor(email: String, password: String) : this(email, password, "Unknown") {
        this.email = email
        this.password = password
    }

    init {
        this.email = email
        this.password = password
        this.name = name
    }
}