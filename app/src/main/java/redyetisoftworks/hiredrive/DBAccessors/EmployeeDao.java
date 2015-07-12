package redyetisoftworks.hiredrive.DBAccessors;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import redyetisoftworks.hiredrive.Models.Employee;

/**
 * Created by Collin Sims on 3/7/2015.
 */
public class EmployeeDao extends BaseDao {

    public static ArrayList<Employee> getAllEmployees(SQLiteDatabase db) {
        ArrayList<Employee> retval = new ArrayList<Employee>();
        String cmd = "SELECT ID, FirstName, LastName, MiddleInitial, Address1, Address2, City, State, Zip, Phone, AltPhone, Email, SSN, MaritalStatus, SpouseName, Veteran, Citizen, Birthdate FROM Employees";
        Employee employee;
        Cursor cursor = db.rawQuery(cmd, null);

        try {
            while (cursor.moveToNext()) {
                employee = new Employee();
                employee.id = cursor.getInt(0);
                employee.firstName = cursor.getString(1);
                employee.lastName = cursor.getString(2);
                employee.middleInitial = cursor.getString(3);
                employee.address1 = cursor.getString(4);
                employee.address2 = cursor.getString(5);
                employee.city = cursor.getString(6);
                employee.state = cursor.getString(7);
                employee.zip = cursor.getString(8);
                employee.phone = cursor.getString(9);
                employee.altPhone = cursor.getString(10);
                employee.email = cursor.getString(11);
                employee.ssn = cursor.getString(12);
                employee.maritalStatus = cursor.getInt(13);
                employee.spouseName = cursor.getString(14);
                employee.isVeteran = cursor.getInt(15) == 1;
                employee.isCitizen = cursor.getInt(16) == 1;
                employee.birthdate = cursor.getString(17);
                retval.add(employee);
            }
        } finally {
            cursor.close();
        }
        return retval;
    }

    public static Employee getEmployeeByID(SQLiteDatabase db, int id) {

        String cmd = "SELECT ID, FirstName, LastName, MiddleInitial, Address1, Address2, City, State, Zip, Phone, AltPhone, Email, SSN, MaritalStatus, SpouseName, Veteran, Citizen, Birthdate FROM Employees WHERE ID = " + id;
        Cursor cursor = db.rawQuery(cmd, null);
        Employee employee = new Employee();
        try {
            while (cursor.moveToNext()) {
                employee.id = cursor.getInt(0);
                employee.firstName = cursor.getString(1);
                employee.lastName = cursor.getString(2);
                employee.middleInitial = cursor.getString(3);
                employee.address1 = cursor.getString(4);
                employee.address2 = cursor.getString(5);
                employee.city = cursor.getString(6);
                employee.state = cursor.getString(7);
                employee.zip = cursor.getString(8);
                employee.phone = cursor.getString(9);
                employee.altPhone = cursor.getString(10);
                employee.email = cursor.getString(11);
                employee.ssn = cursor.getString(12);
                employee.maritalStatus = cursor.getInt(13);
                employee.spouseName = cursor.getString(14);
                employee.isVeteran = cursor.getInt(15) == 1;
                employee.isCitizen = cursor.getInt(16) == 1;
                employee.birthdate = cursor.getString(17);
            }
        } finally {
            cursor.close();
        }
        return employee;
    }

    public static void saveEmployee(final SQLiteDatabase db, final Employee employee) {

        String cmd = "SELECT COUNT(*) FROM Employees WHERE ID = " + employee.id;

        if (getIntValueFromQuery(db, cmd) == 0) {
            db.insertOrThrow("Employees", null, map(employee));
        } else {
            db.update("Employees", map(employee), "ID = ?", new String[]{Integer.toString(employee.id)});
        }
    }

    public static ContentValues map(Employee obj) {
        final ContentValues values = new ContentValues();
        values.put("FirstName", obj.firstName);
        values.put("LastName", obj.lastName);
        values.put("MiddleInitial", obj.middleInitial);
        values.put("Address1", obj.address1);
        values.put("Address2", obj.address2);
        values.put("City", obj.city);
        values.put("State", obj.state);
        values.put("Zip", obj.zip);
        values.put("Phone", obj.phone);
        values.put("AltPhone", obj.altPhone);
        values.put("Email", obj.email);
        values.put("SSN", obj.ssn);
        values.put("MaritalStatus", obj.maritalStatus);
        values.put("SpouseName", obj.spouseName);
        values.put("Veteran", obj.isVeteran ? "1" : "0");
        values.put("Citizen", obj.isCitizen ? "1" : "0");
        values.put("Birthdate", obj.birthdate);
        return values;
    }

    public static void deleteEmployee(SQLiteDatabase db, int empID){
        db.execSQL("DELETE FROM Employees WHERE ID = " + empID);
    }
}
