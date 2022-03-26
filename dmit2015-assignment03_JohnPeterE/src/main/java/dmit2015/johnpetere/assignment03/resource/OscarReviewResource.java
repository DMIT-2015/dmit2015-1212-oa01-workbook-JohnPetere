package dmit2015.johnpetere.assignment03.resource;


import common.validator.BeanValidator;
//import dmit2015.entity.OscarReview;
//import dmit2015.dto.OscarReviewDto;
import dmit2015.johnpetere.assignment03.dto.OscarReviewDto;
import dmit2015.johnpetere.assignment03.entity.OscarReview;
import dmit2015.johnpetere.assignment03.mapper.OscarReviewMapper;
import dmit2015.johnpetere.assignment03.repository.OscarReviewRepository;
//import dmit2015.mapper.OscarReviewMapper;
//import dmit2015.repository.OscarReviewRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.glassfish.jaxb.core.v2.model.core.ID;

import java.net.URI;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("OscarReviewDtos")                    // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)    // All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)    // All methods returns data that has been converted to JSON format
public class OscarReviewDtoResource {

    @Inject
    private OscarReviewRepository _oscarReviewRepository;

    @GET    // This method only accepts HTTP GET requests.
    public Response listOscarReviews() {
        return Response.ok(
                _oscarReviewRepository
                        .list()
                        .stream()
                        .map(OscarReviewMapper.INSTANCE::toDto)
                        .collect(Collectors.toList())
        ).build();
    }

    @Path("{id}")
    @GET    // This method only accepts HTTP GET requests.
    public Response findOscarReviewById(@PathParam("id") ID oscarReviewId) {
        OscarReview existingOscarReview = _oscarReviewRepository.findOptional(oscarReviewId).orElseThrow(NotFoundException::new);

        OscarReviewDto dto = OscarReviewMapper.INSTANCE.toDto(existingOscarReview);

        return Response.ok(dto).build();
    }

    @POST    // This method only accepts HTTP POST requests.
    public Response addOscarReview(OscarReviewDto dto, @Context UriInfo uriInfo) {
        OscarReview newOscarReview = OscarReviewMapper.INSTANCE.toEntity(dto);

        String errorMessage = BeanValidator.validateBean(OscarReview.class, newOscarReview);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        try {
            // Persist the new OscarReview into the database
            _oscarReviewRepository.create(newOscarReview);
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // userInfo is injected via @Context parameter to this method
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(newOscarReview.getId() + "")
                .build();

        // Set the location path of the new entity with its identifier
        // Returns an HTTP status of "201 Created" if the OscarReview was successfully persisted
        return Response
                .created(location)
                .build();
    }

    @PUT            // This method only accepts HTTP PUT requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response updateOscarReview(@PathParam("id") ID id, OscarReviewDto dto) {
        if (!id.equals(dto.getId())) {
            throw new BadRequestException();
        }

        OscarReview existingOscarReview = _oscarReviewRepository
                .findOptional(id)
                .orElseThrow(NotFoundException::new);

        OscarReview updatedOscarReview = OscarReviewMapper.INSTANCE.toEntity(dto);

        String errorMessage = BeanValidator.validateBean(OscarReview.class, updatedOscarReview);
        if (errorMessage != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessage)
                    .build();
        }

        // TODO: copy properties from the updated entity to the existing entity such as copy the version property shown below
//        existingOscarReview.setVersion(updatedOscarReview.getVersion());

        try {
            _oscarReviewRepository.update(existingOscarReview);
        } catch (OptimisticLockException ex) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("The data you are trying to update has changed since your last read request.")
                    .build();
        } catch (Exception ex) {
            // Return an HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "200 OK" and include in the body of the response the object that was updated
        return Response.ok(existingOscarReview).build();
    }

    @DELETE            // This method only accepts HTTP DELETE requests.
    @Path("{id}")    // This method accepts a path parameter and gives it a name of id
    public Response delete(@PathParam("id") ID oscarReviewId) {

        OscarReview existingOscarReview = _oscarReviewRepository
                .findOptional(oscarReviewId)
                .orElseThrow(NotFoundException::new);

        try {
            _oscarReviewRepository.remove(existingOscarReview);    // Removes the OscarReview from being persisted
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response
                    .serverError()
                    .encoding(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "204 No Content" if the OscarReview was successfully deleted
        return Response.noContent().build();

    }

}

