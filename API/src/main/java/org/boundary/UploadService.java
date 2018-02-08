package org.boundary;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;
import java.util.Map;

@Path("file")
public class UploadService {

    @GET
    @Path("download/{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(
            @PathParam("filename") String filename) {
        File file = new File("/opt/jboss/wildfly/standalone/tmp/" + filename);
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment;filename=" + filename);
        return response.build();
    }

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFichier(MultipartFormDataInput input) {

        Map<String, List<InputPart>> formulaire = input.getFormDataMap();
        List<InputPart> inputParts = formulaire.get("img");

        for (InputPart ip : inputParts) {
            MultivaluedMap<String, String> headers = ip.getHeaders();
            String filename = getFileName(headers);

            try {
                InputStream is = ip.getBody(InputStream.class, null);
                byte[] bytes = UploadService.toByteArray(is);
                writeFile(bytes, "/opt/jboss/wildfly/standalone/tmp/" + filename);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        String output = "{ Fichier disponible } ";
        return Response.status(200).entity(output).build();
    }

    public static byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[4096];
            int n = 0;
            while ((n = is.read(b)) != -1) {
                output.write(b, 0, n);
            }
            return output.toByteArray();
        } finally {
            output.close();
        }
    }

    private void writeFile(byte[] contenu, String filename) throws IOException {

        File file = new File(filename);

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(contenu);
        fop.flush();
        fop.close();
    }

    private String getFileName(MultivaluedMap<String, String> headers) {

        String[] contenuHeader = headers.getFirst("Content-Disposition").split(";");

        for (String filename : contenuHeader) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                return name[1].trim().replaceAll("\"", "");
            }
        }

        return "{ inconnu }";
    }


}
