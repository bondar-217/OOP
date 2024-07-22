package rw;

import java.io.*;
import family.FamilyTree;

public class FileHandler implements Writer {
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;

    @Override
    public void write(String filename, FamilyTree tree) throws IOException {
        objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(filename));
        objectOutputStream.writeObject(tree);
        for (Serializable human : tree.getTree()){
            objectOutputStream.writeObject(human);
        }
        objectOutputStream.close();
    }

    @Override
    public FamilyTree read(String filename) throws IOException, ClassNotFoundException {
        objectInputStream = new ObjectInputStream(
                new FileInputStream(filename));
        FamilyTree tree = (FamilyTree) objectInputStream.readObject();
        objectInputStream.close();
        return tree;
    }

}
