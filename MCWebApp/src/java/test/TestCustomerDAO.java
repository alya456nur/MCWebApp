package test;

import dao.CustDashboardDAO;
import view.CustDashboardView;

import java.util.List;

public class TestCustomerDAO {

    public static void main(String[] args) {

        CustDashboardDAO dao = new CustDashboardDAO();

        System.out.println("=== Eyes Variations ===");
        List<CustDashboardView> eyes = dao.getEyesVariations();
        for (CustDashboardView e : eyes) {
            System.out.println(e.getProdName() + " | " + e.getVarName() + " | " +
                               e.getVarPrice() + " | " + e.getVarId() + " | " + e.getImgPath());
        }

        System.out.println("\n=== Lips Products + Variations ===");
        List<CustDashboardView> lips = dao.getLipsVariations();
        for (CustDashboardView l : lips) {
            System.out.println(l.getProdName() + " | " + l.getVarName() + " | " +
                               l.getVarPrice() + " | " + l.getVarId() + " | " + l.getSku() + " | " + l.getImgPath());
        }
    }
}
