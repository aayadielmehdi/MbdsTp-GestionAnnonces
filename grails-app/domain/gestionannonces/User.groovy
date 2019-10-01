package gestionannonces

class User {

    String username
    String password
    Date dateCreated  // faut bien l'ecrire pour que grails les gerer automatiquement
    Date lastUpdated  // si non faut ecrire ce que tu veux et le gerer toi meme
    Illustration thumbnail

    static hasMany = [annonces:Annonce]

    static constraints = {
        username nullable: false,black:false,size: 5..20
        password password:true,nullable: false,black:false , size: 8..30
        thumbnail nullable: false
        annonces nullable: true
    }
}
