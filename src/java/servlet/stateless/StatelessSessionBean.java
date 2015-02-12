/*
 * Copyright (c) 2010, Oracle. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of Oracle nor the names of its contributors
 *   may be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package servlet.stateless;

import Entities.Parameter;
import Entities.Store;
import HttpConnections.ResponseContents;
import autoScenarios.openApi.v1.sellerItems.AutoScenario;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.List;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import jpa.ParameterJpaController;

@Stateless
public class StatelessSessionBean implements Serializable{
    AutoScenario autoScenario;

    public String sayHello(String name) {
        return "Search Input: " + name + "\n";
    }
    
    public int countScenario(String env, String shop) throws NamingException{
        this.autoScenario = new AutoScenario();
        this.autoScenario.addRequest(env, shop);
        return this.autoScenario.getScenarioListSize();
    }
    
    public ResponseContents executeScenario(String env, String shop ,int index) throws IOException, URISyntaxException, NamingException{
        this.autoScenario = new AutoScenario();
        this.autoScenario.addRequest(env, shop);
        return this.autoScenario.executeScenario(index);
    }

    public int insertParameter(String name, String value, int fix) throws NamingException{
        this.autoScenario = new AutoScenario();
        return this.autoScenario.parameterInsert(name, value, fix);
    }
    
    public String getRequest() throws NamingException{
        this.autoScenario = new AutoScenario();
        return this.autoScenario.getRequest();
    }
    
    public boolean insereNovoCenario(){
        return this.autoScenario.insereNovoCenario();
    }
}
