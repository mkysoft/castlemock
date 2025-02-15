/*
 * Copyright 2018 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.service.mock.soap.project;

import com.castlemock.model.core.ServiceResult;
import com.castlemock.model.core.ServiceTask;
import com.castlemock.model.mock.soap.domain.SoapMockResponse;
import com.castlemock.model.mock.soap.domain.SoapMockResponseTestBuilder;
import com.castlemock.repository.soap.project.SoapMockResponseRepository;
import com.castlemock.service.mock.soap.project.input.UpdateSoapMockResponseInput;
import com.castlemock.service.mock.soap.project.output.UpdateSoapMockResponseOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Karl Dahlgren
 */
public class UpdateSoapMockResponseServiceTest {


    @Mock
    private SoapMockResponseRepository mockResponseRepository;

    @InjectMocks
    private UpdateSoapMockResponseService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcess(){
        final String projectId = "ProjectId";
        final String portId = "PortId";
        final String operation = "OperationId";
        final SoapMockResponse mockResponse = SoapMockResponseTestBuilder.builder().build();

        final UpdateSoapMockResponseInput input = UpdateSoapMockResponseInput.builder()
                .projectId(projectId)
                .portId(portId)
                .operationId(operation)
                .mockResponseId(mockResponse.getId())
                .body(mockResponse.getBody())
                .httpHeaders(mockResponse.getHttpHeaders())
                .httpStatusCode(mockResponse.getHttpStatusCode())
                .name(mockResponse.getName())
                .status(mockResponse.getStatus())
                .usingExpressions(mockResponse.isUsingExpressions())
                .expressionType(mockResponse.getExpressionType())
                .xpathExpressions(mockResponse.getXpathExpressions())
                .build();
        final ServiceTask<UpdateSoapMockResponseInput> serviceTask = new ServiceTask<UpdateSoapMockResponseInput>(input);


        Mockito.when(mockResponseRepository.findOne(mockResponse.getId())).thenReturn(mockResponse);
        Mockito.when(mockResponseRepository.update(Mockito.anyString(), Mockito.any(SoapMockResponse.class))).thenReturn(mockResponse);

        final ServiceResult<UpdateSoapMockResponseOutput> result = service.process(serviceTask);
        final UpdateSoapMockResponseOutput output = result.getOutput();
        final SoapMockResponse returnedSoapMockResponse = output.getMockResponse();

        Mockito.verify(mockResponseRepository, Mockito.times(1)).findOne(mockResponse.getId());
        Mockito.verify(mockResponseRepository, Mockito.times(1)).update(mockResponse.getId(), mockResponse);
        Assert.assertEquals(mockResponse.getId(), returnedSoapMockResponse.getId());
        Assert.assertEquals(mockResponse.getName(), returnedSoapMockResponse.getName());
        Assert.assertEquals(mockResponse.getStatus(), returnedSoapMockResponse.getStatus());
        Assert.assertEquals(mockResponse.getBody(), returnedSoapMockResponse.getBody());
        Assert.assertEquals(mockResponse.getHttpStatusCode(), returnedSoapMockResponse.getHttpStatusCode());
        Assert.assertEquals(mockResponse.getStatus(), returnedSoapMockResponse.getStatus());
        Assert.assertEquals(mockResponse.isUsingExpressions(), returnedSoapMockResponse.isUsingExpressions());
        Assert.assertEquals(mockResponse.getExpressionType(), returnedSoapMockResponse.getExpressionType());
        Assert.assertEquals(mockResponse.getXpathExpressions(), returnedSoapMockResponse.getXpathExpressions());
    }
}
