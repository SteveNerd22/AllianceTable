package io.alliancetable.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Network {
    private String ip;
    private int HTTPPort;
    private int TCPPort;

    public Network(String ip, int HTTPPort, int TCPPort) {
        this.ip = ip;
        this.HTTPPort = HTTPPort;
        this.TCPPort = TCPPort;
    }

    public Network(int HTTPPort, int TCPPort) {
        this("localhost", HTTPPort, TCPPort);
    }

    public Network() {
        this(4567, 4568);
    }


    public String get(String urlString) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            urlString = urlString.replace("/", "%2f");
            URL url = new URL("http://"+ip+":"+HTTPPort+"/"+urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            } else {
                System.err.println("GET request fallita: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Chiudere il BufferedReader e disconnettere la connessione
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    public FileHandle getFile(String urlString, String fileName) {
        HttpURLConnection connection = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            urlString = urlString.replace("/", "%2f");
            URL url = new URL("http://"+ip+":"+HTTPPort+"/asset/"+urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // Impostiamo un Accept generico, cambia se necessario (es. "application/json", "image/png", ecc.)
            connection.setRequestProperty("Accept", "*/*");

            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                // Leggiamo il contenuto
                bis = new BufferedInputStream(connection.getInputStream());

                // Creiamo un FileHandle in LibGDX, ad esempio in una cartella "data" locale
                FileHandle fileHandle = Gdx.files.local(fileName);
                fos = new FileOutputStream(fileHandle.file());

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = bis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                fos.flush();
                return fileHandle;
            } else {
                System.err.println("GET request fallita: " + responseCode);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bis != null)
                    bis.close();
                if(fos != null)
                    fos.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            if(connection != null)
                connection.disconnect();
        }
        return null;
    }


    public List<DirectoryItem> getPublicDir() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("http://"+ip+":"+HTTPPort+"/assets");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                String jsonResponse = responseBuilder.toString();

                // Usa Gson per convertire il JSON in una lista di DirectoryItem
                Gson gson = new Gson();
                Type listType = new TypeToken<List<DirectoryItem>>() {}.getType();
                List<DirectoryItem> publicDir = gson.fromJson(jsonResponse, listType);
                return publicDir;
            } else {
                System.err.println("GET request fallita: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (connection != null)
                connection.disconnect();
        }
        return null;
    }


}
