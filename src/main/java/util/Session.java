package util;

import model.Staff;

public class Session {
    private static Staff currentStaff = null;

    public static void login(Staff s)    { currentStaff = s; }
    public static void logout()          { currentStaff = null; }
    public static boolean isLoggedIn()   { return currentStaff != null; }
    public static Staff getCurrentStaff(){ return currentStaff; }
    public static int    getStaffID()    { return currentStaff != null ? currentStaff.getStaffID() : -1; }
    public static String getFullName()   { return currentStaff != null ? currentStaff.getFullName() : "Unknown"; }
    public static String getRole()       { return currentStaff != null ? currentStaff.getRole() : ""; }
}
