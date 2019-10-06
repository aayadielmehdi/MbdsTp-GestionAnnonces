package gestannonces

import gestionannonces.Annonce
import gestionannonces.AnnonceService
import grails.converters.JSON
import grails.converters.XML
import java.text.SimpleDateFormat

class ApiController {
    AnnonceService annonceService
    def dateFormat = "dd-MM-yyyy"

    def index() {}

    def Annonce() {
        switch (request.getMethod()) {
            case "GET":
                if (!params.id)
                    return response.status = 400
                def annonceInstance = Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status = 404
                response.withFormat {
                    xml { render annonceInstance as XML }
                    json { render annonceInstance as JSON }
                }
                break
            case "POST":
                if (!request.JSON.title || !request.JSON.description || !request.JSON.validTill)
                    return response.status = 400
                def annonceInstance = new Annonce(
                        title: request.JSON.title,
                        description: request.JSON.description,
                        validTill: new SimpleDateFormat(dateFormat).parse(request.JSON.validTill),
                        state: Boolean.TRUE,
                )
                annonceInstance.author = User.get(request.JSON.author)
                annonceService.save(annonceInstance)
                return response.status = 201
                break
            case "PUT":
                if (!params.id)
                    return response.status = 400
                def annonceInstance = Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status = 404
                if (!request.JSON.title || !request.JSON.description || !request.JSON.validTill || !request.JSON.state)
                    return response.status = 400
                annonceInstance.title = request.JSON.title
                annonceInstance.description = request.JSON.description
                annonceInstance.validTill = new SimpleDateFormat(dateFormat).parse(request.JSON.validTill)
                annonceInstance.state = request.JSON.state
                annonceService.save(annonceInstance)
                return response.status = 200
                break

            case "PATCH":
                if (!params.id)
                    return response.status = 400
                def annonceInstance = Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status = 404
                if (request.JSON.validTill)
                    annonceInstance.validTill = (new SimpleDateFormat(dateFormat).parse(request.JSON.validTill))
                if (request.JSON.state)
                    annonceInstance.state = new Boolean(request.JSON.state)
                if (request.JSON.description)
                    annonceInstance.description = request.JSON.description
                if (request.JSON.title)
                    annonceInstance.title = request.JSON.title
                annonceService.save(annonceInstance)
                return response.status = 200
                break
            case "DELETE":
                if (!params.id)
                    return response.status = 400
                def annonceInstance = Annonce.get(params.id)
                if (!annonceInstance)
                    return response.status = 404
                annonceInstance.delete(flush: true)
                return response.status = 200
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }


    def annonces() {
        switch (request.getMethod()) {
            case "GET":
                def annonces = Annonce.getAll()
                if (!annonces)
                    return response.status = 404
                response.withFormat {
                    json { render annonces as JSON }
                    xml { render annonces as XML }
                }
                break
            case "POST":
                if (!request.JSON.title || !request.JSON.description || !request.JSON.validTill)
                    return response.status = 400
                def annonceInstance = new Annonce(
                        title: request.JSON.title,
                        description: request.JSON.description,
                        validTill: new SimpleDateFormat(dateFormat).parse(request.JSON.validTill),
                        state: Boolean.TRUE,
                )
                annonceInstance.author = User.get(request.JSON.author)
                annonceService.save(annonceInstance)
                return response.status = 201
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }

}
