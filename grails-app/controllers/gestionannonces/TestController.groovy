package gestionannonces

import grails.converters.JSON

class TestController {

    def index() {


//        User.findAllByUsername("Elmehdi")   elle peut etre encore plus longue

        // afficher la liste des annonces
        render Annonce.list() as JSON
    }
}
