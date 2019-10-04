package gestionannonces

class CronUpdateAnnonceJob {

    AnnonceService annonceService



    static triggers = {
        simple repeatInterval: 5000l // execute job once in 5 seconds
    }

    def execute() {

        // cette methode est  mieux si on a trop de donnée

        def result
        Annonce.withTransaction {
            result = Annonce.executeUpdate "update Annonce a set  a.state = 0  where a.validTill < NOW() AND a.state = 1"
        }

        if (result != 0){
            println("Mise a jour annonce est fait !! ")
        }


//        def an = Annonce.findAllByState(Boolean.TRUE)
//        an.each {
//            Annonce annonce ->
//            if(annonce.validTill.before(new Date())){
//                println("l'annonce "+ annonce.id + " est desactivé mnt")
//                annonce.state = Boolean.FALSE
//                annonceService.save(annonce)
//            }
//        }

    }
}
