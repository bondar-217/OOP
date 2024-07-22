package family;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Human implements Serializable {
    private final String name;
    private final LocalDate birthDate;
    private LocalDate deathDate;
    private final Gender gender;
    private Human mother, father;
    private Set<Human> children;

    public Human(String name, LocalDate birthDate, Gender gender, Human mother, Human father){
        this(name, birthDate, gender);
        setParents(mother);
        setParents(father);
    }

    // Этот конструктор для корневых элементов дерева, для которых не указываются родители
    public Human(String name, LocalDate birthDate, Gender gender){
        children = new HashSet<>();
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public HashSet<Human> getParents(){
        HashSet<Human> set = new HashSet<>();
        if (mother != null)
            set.add(mother);
        if (father != null)
            set.add(father);
        return set;
    }

    public String getName(){
        return name;
    }

    // Теперь возраст выдает сколько полных лет

    public int getAge(){
        LocalDate endDate;
        if (deathDate == null){
            endDate = LocalDate.now();
        } else endDate = deathDate;
        Period period = Period.between(birthDate, endDate);
        return period.getYears();
    }

    // Нижеследующие 2 метода для того, чтобы проследить родословную по конкретной линии

    public Human getMother(){
        if (mother != null)
            return mother;
        return null;
    }

    public Human getFather(){
        if (father != null)
            return father;
        return null;
    }

    // Подойдет для поиска братьев, сестер, дядь, теть, племянников
    //(Перенесено в FamilyTree)

    public Human getChildByName(String name){
        for (Human child : children){
            if(child.getName().equals(name)){
                return child;
            }
        }
        return null;
    }

    // на случай, если родители не были указаны в конструкторе.
    // может также понадобиться при продлении дерева вверх
    // исправлено
    public void setParents(Human parent){
        if (parent.gender == Gender.Female)
            this.mother = parent;
        else this.father = parent;
    }

    // дело сделано
    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    public void addChild(Human child){
        children.add(child);
    }

    public Set<Human> getChildren(){
        return children;
    }

    @Override
    public String toString() {
        return name + " " + getAge() + " y.o. " + gender.toString();

    }

    // проверка на детей. т.к. гипотетически могут быть тёзки-близнецы, но с разным набором детей
    // они не будут равны (исправлено)
    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().getName().equals(this.getClass().getName()))
            return false;

        Human human = (Human) obj;
        if (this.hashCode() != human.hashCode())
            return false;
        return this.name.equals(human.getName()) && this.birthDate.isEqual(human.birthDate)
                && this.children.equals(human.children);
    }

    private int setSize(){
        if (children == null){
            return 0;
        } else return children.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate, mother, father, this.setSize());
    }

    // Поиск предков и потомков в n-ном поколении перенесены в FamilyTree
}
