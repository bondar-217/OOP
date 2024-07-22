package family;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import family.Gender;


public class FamilyTree implements Serializable {
    private Set<Human> humans;

    public FamilyTree(Set<Human> set){
        humans = new HashSet<>(set);
    }

    public FamilyTree() {
        humans = new HashSet<>();
    }

    public void add(Human human) {
        humans.add(human);
    }

    public Set<Human> getTree() {
        return humans;
    }

    public void setParentRelationship(Human human) {
        if (!human.getParents().isEmpty()) {
            for (Human parent : human.getParents()) {
                if (!parent.getChildren().contains(human)) {
                    parent.addChild(human);
                }
            }
        }
    }

    //
    public void setAllRelationships(){
        for (Human human : humans){
            setParentRelationship(human);
        }
    }

    public Human getByName(String name){
        for (Human human : humans){
            if (human.getName().equals(name)){
                return human;
            }
        }
        return null;
    }

    public Set<Human> getSiblings(Human human) {
        Set<Human> siblings = new HashSet<>();
        for (Human parent : human.getParents()) {
            for (Human el : parent.getChildren()) {
                if (!el.equals(human)) {
                    siblings.add(el);
                }
            }
        }
        return siblings;
    }

    public HashSet<Human> getDescendants(Human human, int generation) {
        HashSet<Human> hs = new HashSet<>();
        if (generation == 0) {
            hs.addAll(this.getSiblings(human));
            hs.add(human);
        }
        for (Human child : human.getChildren()) {
            hs.addAll(getDescendants(child, generation - 1));
        }
        return hs;
    }

    public HashSet<Human> getAncestors(Human human, int generation){
        HashSet<Human> hs = new HashSet<>();
        if (generation == 0){
            if (human != null){
                hs.add(human);
            }
        }
        for (Human parent : human.getParents()){
            if (parent != null){
                hs.addAll(getAncestors(parent, generation - 1));
            }
        }
        return hs;
    }
}
