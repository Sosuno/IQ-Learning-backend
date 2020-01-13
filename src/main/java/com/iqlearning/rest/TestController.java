package com.iqlearning.rest;


import com.iqlearning.database.entities.*;
import com.iqlearning.database.service.*;
import com.iqlearning.database.utils.FilledQuestion;
import com.iqlearning.database.utils.FullTest;
import com.iqlearning.rest.resource.TestForm;
import org.apache.commons.text.WordUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;

@RestController
@CrossOrigin(origins=("http://localhost:3000"))
public class TestController {

    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private TestService testService;

    @PutMapping("/test/add")
    public ResponseEntity<?> addTest(@RequestHeader Map<String, String> headers, @RequestBody TestForm testForm) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(testForm.getQuestions().length == 0) return new ResponseEntity<>("Empty question list", HttpStatus.BAD_REQUEST);
        Long[] questionList = testForm.getQuestions();
        for(Long id : questionList) {
            if (questionService.get(id) == null) {
                return new ResponseEntity<>("Question " + id + " not found", HttpStatus.BAD_REQUEST);
            } else if (questionService.get(id).getOwner() != user.getId() && !questionService.get(id).isShareable()) {
                return new ResponseEntity<>("Question " + id + " is not shareable and is not owned by you", HttpStatus.BAD_REQUEST);
            } else if (questionService.get(id).getSubject() != testForm.getSubjectId()) {
                return new ResponseEntity<>("Question " + id + " is not of the same subject as test", HttpStatus.BAD_REQUEST);
            }
        }
        Test newTest = new Test(user.getId(), testForm.getSubjectId(), questionList, testForm.getShareable(), 0, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        Test addedTest = testService.saveTest(newTest);
        if(addedTest != null) {
            return new ResponseEntity<>(addedTest, HttpStatus.OK);
        } else return new ResponseEntity<>("Saving test failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/test/delete/{id}")
    public ResponseEntity<?> deleteTest(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        Test testToDelete = testService.getTest(id);
        if(testToDelete == null) {
            return new ResponseEntity<>("Test not found", HttpStatus.BAD_REQUEST);
        } else if(testToDelete.getOwner() != user.getId()) {
            return new ResponseEntity<>("You are not the owner", HttpStatus.UNAUTHORIZED);
        } else {
            testService.deleteTest(testToDelete);
            if(testService.getTest(id) == null)
                return new ResponseEntity<>( "Test deleted", HttpStatus.OK);
            else return new ResponseEntity<>( "Database error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/test/update")
    public ResponseEntity<?> updateTest(@RequestHeader Map<String, String> headers, @RequestBody TestForm testForm) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(testForm.getQuestions().length == 0) return new ResponseEntity<>("Empty question list", HttpStatus.BAD_REQUEST);
        Long testId = testForm.getId();
        if(testService.getTest(testId) == null) return new ResponseEntity<>("Please specify valid test id", HttpStatus.BAD_REQUEST);
        if(testService.getTest(testId).getOwner() != user.getId()) return new ResponseEntity<>("You're not the owner of the test you're trying to edit", HttpStatus.UNAUTHORIZED);
        Long[] questionList = testForm.getQuestions();
        Test editedTest = testService.getTest(testId);
        if(testForm.getSubjectId() != null) editedTest.setSubject(testForm.getSubjectId());
        for(Long id : questionList) {
                if (questionService.get(id) == null) {
                    return new ResponseEntity<>("Question " + id + " not found", HttpStatus.BAD_REQUEST);
                } else if (questionService.get(id).getOwner() != user.getId() && !questionService.get(id).isShareable()) {
                    return new ResponseEntity<>("Question " + id + " is not shareable and is not owned by you", HttpStatus.BAD_REQUEST);
                } else if (questionService.get(id).getSubject() != editedTest.getSubject()) {
                    return new ResponseEntity<>("Question " + id + " is not of the same subject as test", HttpStatus.BAD_REQUEST);
                }
        }
        editedTest.setQuestions(questionList);
        if(editedTest.isShareable() != testForm.getShareable()) {
            editedTest.setShareable(testForm.getShareable());
        }
        editedTest.setLastEdited(new Timestamp(System.currentTimeMillis()));
        Test savedTest = testService.saveTest(editedTest);
        if(savedTest != null) {
            return new ResponseEntity<>(savedTest, HttpStatus.OK);
        } else return new ResponseEntity<>("Saving test failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/tests/get/user")
    public ResponseEntity<?> getTestsByUser(@RequestHeader Map<String, String> headers){
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        List<Test> testList = testService.getTestsByUser(user.getId());
        List<FullTest> fullTestList = new ArrayList<>();
        for(Test t : testList) {
            fullTestList.add(testFiller(t));
        }
        return new ResponseEntity<>(fullTestList, HttpStatus.OK);
    }

    @GetMapping("/tests/get/user/subject/{id}")
    public ResponseEntity<?> getTestsBySubject(@RequestHeader Map<String, String> headers, @PathVariable Long id){
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        Subject subject = subjectService.getSubject(id);
        if(subject.getId() == null) return new ResponseEntity<>("Invalid subject", HttpStatus.BAD_REQUEST);
        List<Test> testList = testService.getTestsByUserAndSubject(user.getId(), id);
        List<FullTest> fullTestList = new ArrayList<>();
        for(Test t : testList) {
            fullTestList.add(testFiller(t));
        }
        return new ResponseEntity<>(fullTestList, HttpStatus.OK);
    }

    @GetMapping("/test/get/{id}")
    public ResponseEntity<?> getTestById(@RequestHeader Map<String, String> headers, @PathVariable Long id){
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }

        Test test = testService.getTest(id);
        if(test == null) return new ResponseEntity<>("Test not found", HttpStatus.BAD_REQUEST);
        if(test.getOwner() != user.getId()) return new ResponseEntity<>("You're not the owner", HttpStatus.UNAUTHORIZED);
        FullTest fullTest = testFiller(test);
        return new ResponseEntity<>(fullTest, HttpStatus.OK);
    }

    @GetMapping("/tests/get/public")
    public ResponseEntity<?> getPublicQuestions(@RequestHeader Map<String, String> headers) {
        String session = headers.get("authorization").split(" ")[1];
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        List<Test> testList = testService.getSharedTests();
        List<FullTest> fullTestList = new ArrayList<>();
        for(Test t : testList) {
            fullTestList.add(testFiller(t));
        }
        return new ResponseEntity<>(fullTestList, HttpStatus.OK);
    }

    @GetMapping("/tests/get/public/subject/{id}")
    public ResponseEntity<?> getPublicQuestionsBySubject(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        String session = headers.get("authorization").split(" ")[1];
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        Subject subject = subjectService.getSubject(id);
        if(subject.getId() == null) return new ResponseEntity<>("Invalid subject", HttpStatus.BAD_REQUEST);
        List<Test> testList = testService.getSharedTestsBySubject(id);
        List<FullTest> fullTestList = new ArrayList<>();
        for(Test t : testList) {
            fullTestList.add(testFiller(t));
        }
        return new ResponseEntity<>(fullTestList, HttpStatus.OK);
    }

    @GetMapping("/test/print/{id}/{groups}")
    public ResponseEntity<?> getPrintableTestById(@RequestHeader Map<String, String> headers, @PathVariable Long id, @PathVariable Long groups) throws IOException {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        Test test = testService.getTest(id);
        if(test == null) return new ResponseEntity<>("Test not found", HttpStatus.BAD_REQUEST);
        if(test.getOwner() != user.getId()) return new ResponseEntity<>("You're not the owner", HttpStatus.UNAUTHORIZED);
        if(groups<1 || groups>4) return new ResponseEntity<>("You must specify a number of groups that is between 1 and 4", HttpStatus.BAD_REQUEST);
        FullTest fullTest = testFiller(test);
        List<FilledQuestion> filledQuestionList = fullTest.getQuestions();
        PDDocument document = new PDDocument();
        PDPageContentStream contentStream = null;
        PDFont font = PDType1Font.HELVETICA_BOLD;
        int offsetY;
        List<String> answers = Arrays.asList("    a) ", "    b) ", "    c) ", "    d) ");
        for(int i = 1; i <= groups; i++) {
            PDPage page = new PDPage();
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(font, 12);
            offsetY = 760;
            int questionCounter = 1;
            String groupString = "GROUP " + i;
            if(groups != 1) {
                contentStream.beginText();
                contentStream.newLineAtOffset(255, offsetY);
                contentStream.showText("NAME: ....................     SURNAME: ....................     " + groupString);
                contentStream.endText();
            } else {
                contentStream.beginText();
                contentStream.newLineAtOffset(335, offsetY);
                contentStream.showText("NAME: ....................     SURNAME: .................... " );
                contentStream.endText();
            }
            offsetY -= 50;
            for(FilledQuestion q : filledQuestionList) {
                boolean counterShown = false;
                // checking if it will fit on current page
                if((q.getQuestion().length()/80 > offsetY/26) || (q.isChoiceTest() && (q.getAnswers().size() > 2) && (offsetY <= 150)) || offsetY < 100) {
                    //new page
                    page = new PDPage();
                    document.addPage(page);
                    contentStream.close();
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(font, 12);
                    offsetY = 760;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(235,  offsetY);
                    contentStream.showText("NAME: ....................     SURNAME: ....................          " + groupString);
                    contentStream.endText();
                    offsetY -= 50;
                }
                if(q.getQuestion().length() > 80) {
                    String question = WordUtils.wrap(q.getQuestion(), 80);
                    String[] lines = question.split("\\r?\\n");
                    for(String s : lines) {
                        if(!counterShown) {
                            contentStream.beginText();
                            contentStream.newLineAtOffset(25,  offsetY);
                            contentStream.showText(questionCounter + ". " + s);
                            contentStream.endText();
                            counterShown = true;
                            offsetY-=20;
                        } else {
                            contentStream.beginText();
                            contentStream.newLineAtOffset(25,  offsetY);
                            contentStream.showText("    " + s);
                            contentStream.endText();
                            offsetY-=20;
                        }
                    }
                    questionCounter++;
                } else {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(25,  offsetY);
                    contentStream.showText(questionCounter + ". " + q.getQuestion());
                    contentStream.endText();
                    questionCounter++;
                    offsetY -= 20;
                }
                int answerCounter = 0;
                if(!q.isChoiceTest()) offsetY -= 10;
                if(q.isChoiceTest()) {
                    List<Answer> answerList = questionService.getAnswers(q.getId());
                    // changing order of answers for different groups
                    if(i != 1) answerList = answersShuffler(answerList,i);

                    for(Answer a : answerList) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(25,  offsetY);
                        contentStream.showText(answers.get(answerCounter) + a.getAnswer());
                        contentStream.endText();
                        offsetY -= 20;
                        answerCounter++;
                    }
                    offsetY -= 10;
                }
            }
            contentStream.close();
        }
        // filling answer card
        PDPage newPage = new PDPage();
        document.addPage(newPage);
        contentStream = new PDPageContentStream(document, newPage);
        contentStream.setFont(font, 12);
        offsetY = 760;
        for(int i = 1; i <= groups; i++) {
            int questionCounter = 1;
            int answerCounter = 0;
            String groupString = "GROUP " + i;
            contentStream.beginText();
            contentStream.newLineAtOffset(25,  offsetY);
            if(groups == 1) contentStream.showText("CORRECT ANSWERS");
            else contentStream.showText("CORRECT ANSWERS FOR " + groupString);
            contentStream.endText();
            offsetY -= 30;
            for(FilledQuestion q: filledQuestionList) {
                // checking if it will fit on current page
                if(offsetY < 45) {
                    //new page
                    PDPage page = new PDPage();
                    document.addPage(page);
                    contentStream.close();
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(font, 12);
                    offsetY = 760;
                }
                if(q.isChoiceTest()) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(25,  offsetY);
                    contentStream.showText(questionCounter+". ");
                    contentStream.endText();
                    questionCounter++;
                    List<Answer> answerList = questionService.getAnswers(q.getId());
                    // changing order of answers for different groups
                    if(i != 1) answerList = answersShuffler(answerList,i);
                    answerCounter = 0;
                    for(Answer a : answerList) {
                        if(a.isCorrect()) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(35,  offsetY);
                        contentStream.showText(answers.get(answerCounter));
                        contentStream.endText();
                        offsetY -= 10;
                        }
                        answerCounter++;
                    }
                    offsetY -= 10;
                } else questionCounter++;
            }
            offsetY -= 20;
        }
        contentStream.close();

        document.save("test.pdf");
        document.close();

        // convert PDF to base64
        byte[] input_file = Files.readAllBytes(Paths.get("test.pdf"));
        byte[] encodedBytes = Base64.getEncoder().encode(input_file);
        String encodedString =  new String(encodedBytes);
        // decoding pdf
        /*
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString.getBytes());
        FileOutputStream fos = new FileOutputStream("decoded.pdf");
        fos.write(decodedBytes);
        fos.close();
        */
        return new ResponseEntity<>(encodedString, HttpStatus.OK);
    }

    private FullTest testFiller(Test test) {
        List<Question> questionList = new ArrayList<>();
        for(Long l : test.getQuestions()) {
            Question q = questionService.get(l);
            if(q != null) questionList.add(q);
        }
        List<FilledQuestion> filledQuestionList = questionService.getReadyQuestions(questionList);
        FullTest fullTest = new FullTest(test, filledQuestionList);
        return fullTest;
    }

    private List<Answer> answersShuffler(List<Answer> answerList, int groupnumber) {
        List<Answer> subList;
        List<Answer> subList2;
        List<Answer> modifiedAnswerList = new ArrayList<>();
        switch(groupnumber) {
            case 1: break;
            case 2: {
                Collections.reverse(answerList);
                modifiedAnswerList = answerList;
                break;
            }
            case 3: {
                subList = answerList.subList(0, answerList.size()/2);
                subList2 = answerList.subList(answerList.size()/2,answerList.size());
                Collections.reverse(subList);
                Collections.reverse(subList2);
                modifiedAnswerList.addAll(subList);
                modifiedAnswerList.addAll(subList2);
                break;
            }
            case 4: {
                subList = answerList.subList(0, answerList.size()/2);
                subList2 = answerList.subList(answerList.size()/2,answerList.size());
                modifiedAnswerList.clear();
                modifiedAnswerList.addAll(subList2);
                modifiedAnswerList.addAll(subList);
                break;
            }
        }
        return modifiedAnswerList;
    }

}
