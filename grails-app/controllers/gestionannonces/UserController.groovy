package gestionannonces

import grails.validation.ValidationException
import org.springframework.web.multipart.MultipartHttpServletRequest

import static org.springframework.http.HttpStatus.*

class UserController {

    UserService userService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond userService.list(params), model:[userCount: userService.count()]
    }

    def show(Long id) {
        respond userService.get(id)
    }


    def create() {
        respond new User(params)
    }

    def save(User user) {
        if (user == null) {
            notFound()
            return
        }

        try {

//            definir la méthode si c'est creation ou edition
            def methode = params.methode

            def x = request.getFile('fileUpload')  // x est le fichier inserer en upload

            if (x == null || x.empty){
                if (methode == "creation"){
                    // ajout d'une image par defaut en cas de non saisie d'image à l'ajout au modification pas de blem
                    user.thumbnail = new Illustration(filename: grailsApplication.config.maConfig.avatar_default)
                }
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
                    x.transferTo(File)
                    user.thumbnail = new Illustration(filename: randomString)  // si c'est de nombreux avatar faux faire user.addtouser
                }
            }

            userService.save(user)


        } catch (ValidationException e) {
            respond user.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*' { respond user, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond userService.get(id)
    }

    def update(User user) {
        if (user == null) {
            notFound()
            return
        }

        try {
            userService.save(user)
        } catch (ValidationException e) {
            respond user.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*'{ respond user, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        userService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

}
