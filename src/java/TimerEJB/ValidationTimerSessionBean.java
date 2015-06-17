/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimerEJB;

import autoScenarios.openApi.v1.sellerItems.AutoScenario;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

/**
 *
 * @author fellippe.mendonca
 */
@Singleton
public class ValidationTimerSessionBean {

    AutoScenario autoScenario;

    @Schedule(minute = "1", second = "1", dayOfMonth = "*", month = "*", year = "*", hour = "*", dayOfWeek = "*")

    public void myTimer() {
        System.out.println("Timer event: " + new Date());
        /*int requestId = 179;
        if (executeScenario(requestId)) {
            System.out.println("Concluiu com Sucesso");
        } else {
            System.out.println("Fracassou com Sucesso");
        }*/
        
        
        if (executeAllScenarios()) {
            System.out.println("Concluiu com Sucesso");
        } else {
            System.out.println("Fracassou com Sucesso");
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public boolean executeScenario(int id) {
        boolean success = false;
        try {
            /*----------------------------------------------------------------*/

            autoScenario = new AutoScenario();
            autoScenario.getRequestValidationExecuted(id);
            System.out.println("Request ID executado: " + id);
            success = true;

            /*----------------------------------------------------------------*/
        } catch (URISyntaxException ex) {
            Logger.getLogger(ValidationTimerSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ValidationTimerSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean executeAllScenarios() {
        boolean success = false;
        try {
            /*----------------------------------------------------------------*/
            autoScenario = new AutoScenario();
            System.out.println("Todas as validações estão sendo executadas...");
            success = autoScenario.executeScheduledValidations();
            /*----------------------------------------------------------------*/
        } catch (URISyntaxException ex) {
            Logger.getLogger(ValidationTimerSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ValidationTimerSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            System.out.println("Feito!");
            return success;
        }
    }
}
