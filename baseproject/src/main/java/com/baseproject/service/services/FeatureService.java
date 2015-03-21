package com.baseproject.service.services;

import java.net.URI;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.annotations.Form;

import com.baseproject.model.common.Loader;
import com.baseproject.model.entities.Feature;
import com.baseproject.model.filters.FeatureFilter;
import com.baseproject.service.common.BaseService;
import com.baseproject.service.data.FeatureData;
import com.baseproject.util.validation.ValidationException;

@Path(FeatureService.PATH)
public class FeatureService extends BaseService {
	
	public static final String PATH = "/features";

	@GET
	@Path("/")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchFeatures(@Form FeatureFilter featureFilter) {
		List<Feature> features = Feature.repository().list(featureFilter);
		
		return Response.status(200).entity(FeatureData.unbuild(features)).build();
	}
	 
	@GET
	@Path("/{id}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchFeature(@PathParam("id") Long id, @QueryParam("load") String fields) {
		Feature feature = Feature.repository().fetch(id, Loader.load(fields, Feature.class));
		
		if (feature != null) {
			return Response.status(200).entity(FeatureData.unbuild(feature)).build();
		} else {
			return Response.status(404).build();
		}
	}
	 
	@POST
	@Path("/")
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createFeature(FeatureData featureData) {
		Feature feature = FeatureData.build(featureData);
		
		try {
			feature.prepareForPersist();
			feature = feature.save();
		} catch (ValidationException e) {
			return Response.status(422).entity(e.getValidationFailures()).build();
		}
		
		URI location = UriBuilder.fromPath(PATH + "/" + feature.getId()).build();
		return Response.status(201).location(location).build();
	}
	 
	@PUT
	@Path("/{id}")
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateFeature(@PathParam("id") Long id, FeatureData featureData) {
		Feature persistedFeature = Feature.repository().fetch(id);
		
		if (persistedFeature == null) {
			return Response.status(404).build();
		}
		
		Feature feature = FeatureData.build(featureData);
		
		try {
			feature.prepareForUpdate(persistedFeature);
			feature = feature.save();
		} catch (ValidationException e) {
			return Response.status(422).entity(e.getValidationFailures()).build();
		}
		
		return Response.status(200).build();
	}
	 
	@DELETE
	@Path("/{id}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteFeature(@PathParam("id") Long id) {
		Feature feature = Feature.repository().fetch(id);
		
		if (feature != null) {
			feature.remove();
		} else {
			return Response.status(404).build();
		}
		
		return Response.status(200).build();
	}
}
