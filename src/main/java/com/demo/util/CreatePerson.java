package com.demo.util;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.iai.v20180301.IaiClient;

import com.tencentcloudapi.iai.v20180301.models.CreatePersonRequest;
import com.tencentcloudapi.iai.v20180301.models.CreatePersonResponse;

public class CreatePerson
{
    public static void main(String [] args) {
        try{

            Credential cred = new Credential("AKIDWpuNei0ndJmaJpnYrDRq0uYv1vaS7XaU", "bEbiYOytb5JG0WcWhcI3allsqY0XpETa");
            
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("iai.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);            
            
            IaiClient client = new IaiClient(cred, "ap-guangzhou", clientProfile);
            
            String params = "{\"GroupId\":\"1\",\"PersonName\":\"22\",\"PersonId\":\"123\",\"Url\":\"https://i.loli.net/2019/04/01/5ca1c80591378.png\"}";
            CreatePersonRequest req = CreatePersonRequest.fromJsonString(params, CreatePersonRequest.class);
            
            CreatePersonResponse resp = client.CreatePerson(req);
            
            System.out.println(CreatePersonRequest.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
                System.out.println(e.toString());
        }

    }
    
}