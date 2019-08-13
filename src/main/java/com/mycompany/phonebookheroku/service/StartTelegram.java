
package com.mycompany.phonebookheroku.service;

import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import com.github.badoualy.telegram.api.Kotlogram;
import com.github.badoualy.telegram.api.TelegramApiStorage;
import com.github.badoualy.telegram.api.TelegramApp;
import com.github.badoualy.telegram.api.TelegramClient;
import com.github.badoualy.telegram.tl.api.TLUser;
import com.github.badoualy.telegram.tl.api.auth.TLAuthorization;
import com.github.badoualy.telegram.tl.api.auth.TLSentCode;
import com.github.badoualy.telegram.tl.exception.RpcErrorException;



@Named("startTelegram") // ��� ��������� ���������� ����� � cdi ��� � ������ ��� ������� ��� jsf ��������
@SessionScoped
public class StartTelegram implements Serializable {
    private String kodTelegram;
    private TelegramApp application;
    private boolean accessed; //����� �������� ������ � ���������� ������������� � ������
    private TelegramClient client;
    private TLSentCode sentCode;
    private String phohe_number;
    private String resultOperationTelegram;
    private ApiStorage state; 
    
    public StartTelegram() {
        this.application = new TelegramApp(868145, "f060da0313295164b197cea7de9f5e53", "Model",  "SysVer", "1", "en");
        this.resultOperationTelegram = "������� ����� ��������";
        this.state = new ApiStorage();
        this.client = Kotlogram.getDefaultClient(application, state);
         
    }

    public TelegramApp getApplication() {
        return application;
    }

    public String getResultOperationTelegram() {
        return resultOperationTelegram;
    }

    public TelegramClient getClient() {
        return client;
    }

    public TLSentCode getSentCode() {
        return sentCode;
    }

    public boolean isAccessed() {
        return accessed;
    }

    public String getPhohe_number() {
        return phohe_number;
    }

    public void setPhohe_number(String phohe_number) {
        this.phohe_number = phohe_number;
    }    
    
    public String getKodTelegram() {
        return kodTelegram;
    }

    public void setKodTelegram(String kodTelegram) {
        this.kodTelegram = kodTelegram;
    }
   
    public String goToDataTelegram() {
        if ("".equals(kodTelegram)) {
            this.resultOperationTelegram = "������� ���������� ���";
            return "no value code"; 
        } else{
            //��� ���������� ������ ��������� �� �������� ������� HomeServlet
            //�������� ������ �� ���������� � �������.
           return "success";
        }
    }
    
    public void connectTelegram() { 
        /* ��������� ����������� ��� ��������� ���� ��������� */  
        
      if ("".equals(phohe_number) == false) {
         try{
              sentCode = client.authSendCode(false, phohe_number, true);
              this.accessed = true; 
              this.resultOperationTelegram = "������� ���������� ���";
           } catch (RpcErrorException | IOException e) {
              this.resultOperationTelegram  = e.getMessage();
           } 
       }  
       
    }  
    
    public void changedkodTelegram(ValueChangeEvent e) { 
       //��������� ���������� ��� ��������� ���� ������� ������� � ����� � jsf �������� ��
       //���������� ��� ������ ����� ����������� action �����.
       
    }  

}
