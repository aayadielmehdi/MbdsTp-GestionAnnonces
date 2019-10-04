package gestionannonces

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class AnnonceController {

    AnnonceService annonceService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond annonceService.list(params), model: [annonceCount: annonceService.count()]
    }

    def show(Long id) {
        respond annonceService.get(id)
    }

    def create() {
        respond new Annonce(params)
    }

    def save(Annonce annonce) {
        if (annonce == null) {
            notFound()
            return
        }

        try {


            def x = request.getFiles('illus')

            x.each{

                if (it == null || it.empty){
                    flash.message = "Veuillez saisir les illustrations"
                    return
                }else{

                    def pool = ['a'..'z','A'..'Z',0..9,'_'].flatten()
                    Random rand = new Random(System.currentTimeMillis())
                    def randomTab = (0..10).collect { pool[rand.nextInt(pool.size())] }

                    def randomString =""
                    for (item in randomTab) {
                        randomString = randomString + item
                    }

                    randomString =  randomString + ".png"

                    def File = new File (grailsApplication.config.maConfig.assets_url + randomString) // file de copy

                    if (File.exists()){
                        flash.message = "Fichier déjà existant"
                        return
                    }else{
                        it.transferTo(File)
                        annonce.addToIllustrations(new Illustration(filename: randomString))
                    }
                }



            }

            annonceService.save(annonce)

        } catch (ValidationException e) {
            respond annonce.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'annonce.label', default: 'Annonce'), annonce.id])
                redirect annonce
            }
            '*' { respond annonce, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond annonceService.get(id)
    }

    def update(Annonce annonce) {
        if (annonce == null) {
            notFound()
            return
        }

        try {
            annonceService.save(annonce)
        } catch (ValidationException e) {
            respond annonce.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'annonce.label', default: 'Annonce'), annonce.id])
                redirect annonce
            }
            '*' { respond annonce, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        annonceService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'annonce.label', default: 'Annonce'), id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'annonce.label', default: 'Annonce'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def deleteIllustrationAnnonce() {
        try {
            def annonceInstance = Annonce.get(params.ann_id)
            def illustrationInstance = Illustration.get(params.illustration_id)
            annonceInstance.removeFromIllustrations(illustrationInstance)
            annonceInstance.save(flush : true)   // flush car on est pas en service

            // effacer le fichier physique

            // efface l'element physique

            def file = new File(grailsApplication.config.maConfig.assets_url + illustrationInstance.filename)

            if (file.exists()){
                file.delete()
            }

            illustrationInstance.delete(flush: true)

            redirect(controller: "annonce", action: "edit", id: annonceInstance.id)


        } catch (ValidationException e) {
            flash.message = "Une erreur est survenue"
            return
        }
    }
}
