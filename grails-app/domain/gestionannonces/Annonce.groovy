package gestionannonces

class Annonce {

    String title
    String description
    Date  datecreation
    Date validite
    Boolean State = Boolean.false

    static belongsTo = [author:User]

    static hasMany = [illustrations:Illustration]

    static constraints = {
        title blank: false,nullable: false
        description blank: false,nullable: false
        validite nullable: false
        illustrations nullable: false
    }
}
