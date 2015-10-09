
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
        
        if (executeAllScenarios()) {
            System.out.println("Concluiu com Sucesso");
        } else {
            System.out.println("Fracassou com Sucesso");
        }
    }

    public boolean executeAllScenarios() {
        boolean success = false;
        try {
            /*----------------------------------------------------------------*/
            this.autoScenario = new AutoScenario();
            System.out.println("Todas as validações estão sendo executadas...");
            success = autoScenario.executeScheduledValidations();
            
            /*----------------------------------------------------------------*/
        } catch (URISyntaxException ex) {
            Logger.getLogger(ValidationTimerSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ValidationTimerSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            System.out.println("Feito!");
            this.autoScenario = null;
            return success;
        }
    }
}
