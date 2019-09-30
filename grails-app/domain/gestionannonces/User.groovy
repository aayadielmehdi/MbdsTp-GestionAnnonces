package gestionannonces

class User {

    String username
    String password
    Date datecreation
    Date lastupdate
    Illustration thumbnail

    static hasMany = [annonces:Annonce]

    static constraints = {
        username nullable: false,black:false,size: 5..20
        password password:true,nullable: false,black:false , size: 8..30
        thumbnail nullable: false
        annonces nullable: true
    }
}
