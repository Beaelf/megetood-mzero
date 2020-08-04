package demo.annotation;

@CourseInfoAnnotaton(courseName = "java基础", courseTag = "java", courseProfile = "java反射、注解")
public class MegetoodCourse {
    @PersonInfoAnnotation(name = "Megetodo", language = {"java","Go","python"})
    private String author;
    @CourseInfoAnnotaton(courseName = "spring基础", courseTag = "spring", courseProfile = "spring ioc aop 反射、注解", courseIndex = 133)
    public void getCourseInfo(){

    }
}

