package gestionannonces

class BootStrap {

    def init = { servletContext ->
        def userInstance = new User(username: "username" ,
                "password":"password" ,
                thumbnail: new Illustration(filename: "apple-touch-icon.png"))

        (1..5).each {
            userInstance.addToAnnonces(
                    new Annonce(title: "Titre"+it,
                            description: "Description",
                            validTill: new Date(),
                            state: Boolean.TRUE)
                            .addToIllustrations(new Illustration(filename: "avatar.png"))
                            .addToIllustrations(new Illustration(filename: "apple-touch-icon.png"))
            )
        }
        userInstance.save(flush:true,failOnError:true)
        // tous crach on cas d'erreur
        // flush on utilise quand on a besoin (exemple d'utilisation id utilisateur)
    }

    def destroy = {

    }
}
