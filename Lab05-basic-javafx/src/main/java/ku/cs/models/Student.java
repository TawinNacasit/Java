package ku.cs.models;

public class Student {
    private String id;
    private String name;
    private double score;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        score = 0;
    }

    public Student(String id, String name, double score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public void changeName(String name) {
        if (!name.trim().equals("")) {
            this.name = name.trim();
        }
    }

    public void addScore(double score) {
        if(score <= 0){
            throw new IllegalArgumentException("Name cannot be less than zero");
        }

        this.score += score;
    }

    // TODO: design grading system for Student
    public String grade()
    {
        return (score > 90 )
                ? "A"
                : "F" ;

    }

    public boolean isId(String id) {
        return this.id.equals(id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public String getGrade(){ return grade();}

    @Override
    public String toString() {
        return "{" +
                "id: '" + id + '\'' +
                ", name: '" + name + '\'' +
                ", score: " + score +
                '}';
    }
}