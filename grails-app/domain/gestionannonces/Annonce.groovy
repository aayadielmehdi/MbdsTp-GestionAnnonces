package gestionannonces

class Annonce {

    String title
    String description
    Date  dateCreated
    Date validTill
    Boolean state   = Boolean.FALSE

    static belongsTo = [author:User]

    static hasMany = [illustrations:Illustration]

    static constraints = {
        title blank: false,nullable: false
        description blank: false,nullable: false
        validTill nullable: false
        illustrations nullable: true
    }

    @Override
    String toString() {
        return title
    }

    /*
    Calcul le nombre d'illustration en une annonce



     */
    def countIllustrationOfAnnonce(Long _id){
        def result = Annonce.executeQuery("select count(*) from Annonce a join a.illustrations ill  " +
                "where a.id =:id" , [id:_id] )
        return result
    }
}
