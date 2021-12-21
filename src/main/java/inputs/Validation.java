package inputs;

import com.scheduler.scheduleAPI.model.Contact;

import java.util.InputMismatchException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static boolean checkString(String data, String fieldName) {
        if (data == null)
            throw new InputMismatchException(fieldName + " is required");

        if (data.trim().isEmpty())
            throw new InputMismatchException(fieldName + " cannot be empty");

        return true;
    }

    public static boolean checkNumber(long data, String fieldName) {

        if (data <= 0)
            throw new InputMismatchException(fieldName + " cannot be zero or empty");
        return true;
    }

    public static boolean checkParticipantList(List<Contact> list) {
        if (list == null)
            throw new InputMismatchException("Participants are required");
        if (list.isEmpty())
            throw new InputMismatchException("Participants cannot be empty");
        return true;
    }

    public static boolean checkMobileNumber(String mobileNo) {

        Pattern p = Pattern.compile("^\\d{10}$");
        Matcher m = p.matcher(mobileNo);

        if (m.matches())
            return true;
        else
            throw new InputMismatchException("Enter valid Mobile number");
    }

    public static boolean checkParticipantIds(List<String> list) {
        if (list == null) {
            throw new InputMismatchException("Participants are required");
        }
        if (list.isEmpty())
            throw new InputMismatchException("Participants cannot be empty");
        return true;

    }

    public static boolean checkPassword(String password) {
        checkString(password, "Password");
        if (password.length() < 8)
            throw new InputMismatchException("Password must contain 8 characters");
        return true;
    }
}