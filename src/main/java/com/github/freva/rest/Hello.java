package com.github.freva.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Path("hello")
public class Hello {
    private static final Queue<Visit> lastVisits = new ConcurrentLinkedQueue<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Visit> helloWorld(@Context HttpServletRequest requestContext) {
        Visit visit = new Visit();
        visit.setIpAddress(requestContext.getRemoteAddr());
        visit.setTime(Instant.now());

        lastVisits.add(visit);
        if (lastVisits.size() > 20) {
            lastVisits.poll();
        }

        return Collections.unmodifiableCollection(lastVisits);
    }
}
