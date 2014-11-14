package pro.zackpollard.iopreserve.server.utils;

import java.io.*;
import java.util.HashMap;

/**
 * @Author zack
 * @Date 14/11/14.
 */
public class DataStore {

    private File storageFile;
    private HashMap<String, String> values;

    public DataStore(String path) {
        this.storageFile = new File(path);
        this.values = new HashMap<String, String>();

        if (this.storageFile.exists() == false) {
            try {
                this.storageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            this.load();
        }
    }

    public void load() {
        try {
            DataInputStream input = new DataInputStream(new FileInputStream(this.storageFile));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line;

            while ((line = reader.readLine()) != null) {
                String[] split = line.split(":@:");
                if (this.values.containsKey(split[0]) == false) {
                    this.values.put(split[0], split[1]);
                }
            }
            reader.close();
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            FileWriter stream = new FileWriter(this.storageFile);
            BufferedWriter out = new BufferedWriter(stream);

            for (String value : this.values.keySet()) {
                out.write(value + ":@:" + values.get(value));
                out.newLine();
            }
            out.close();
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean contains(String value) {
        return this.values.containsKey(value);
    }

    public void add(String value, String timestamp) {
        this.values.put(value, timestamp);
    }

    public void remove(String value) {
        this.values.remove(value);
    }

    public HashMap<String, String> getValues() {
        return this.values;
    }
}
