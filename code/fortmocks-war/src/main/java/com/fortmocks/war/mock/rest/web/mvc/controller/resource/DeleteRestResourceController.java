/*
 * Copyright 2015 Karl Dahlgren
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

package com.fortmocks.war.mock.rest.web.mvc.controller.resource;

import com.fortmocks.core.mock.rest.model.project.dto.RestProjectDto;
import com.fortmocks.core.mock.rest.model.project.dto.RestResourceDto;
import com.fortmocks.war.mock.rest.model.project.service.RestProjectService;
import com.fortmocks.war.mock.rest.web.mvc.command.resource.DeleteRestResourceCommand;
import com.fortmocks.war.mock.rest.web.mvc.controller.AbstractRestViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The project controller provides functionality to retrieve a specific project
 * @author Karl Dahlgren
 * @since 1.0
 */
@Controller
@RequestMapping("/web/rest/project")
public class DeleteRestResourceController extends AbstractRestViewController {

    @Autowired
    private RestProjectService restProjectService;
    private static final String PAGE = "mock/rest/project/restProject";
    /**
     * Retrieves a specific project with a project id
     * @param restProjectId The id of the project that will be retrieved
     * @return Project that matches the provided project id
     */
    @PreAuthorize("hasAuthority('READER') or hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/{restResourceId}/delete", method = RequestMethod.GET)
    public ModelAndView defaultPage(@PathVariable final Long restProjectId, @PathVariable final Long restApplicationId, @PathVariable final Long restResourceId) {
        final RestResourceDto restResource = restProjectService.findRestResource(restProjectId, restApplicationId, restResourceId);

        final ModelAndView model = createPartialModelAndView(PAGE);
        model.addObject(REST_PROJECT_ID, restProjectId);
        model.addObject(REST_APPLICATION_ID, restApplicationId);
        model.addObject(REST_RESOURCE, restResource);
        return model;
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{projectId}/application/{applicationId}/resource/{restResourceId}/delete/confirm", method = RequestMethod.GET)
    public ModelAndView confirm(@PathVariable final Long restProjectId, @PathVariable final Long restApplicationId, @PathVariable final Long restResourceId) {
        restProjectService.deleteRestResource(restProjectId, restApplicationId, restResourceId);
        return redirect("/rest/project/" + restProjectId);
    }

    @PreAuthorize("hasAuthority('MODIFIER') or hasAuthority('ADMIN')")
    @RequestMapping(value = "/{restProjectId}/application/{restApplicationId}/resource/delete/confirm", method = RequestMethod.POST)
    public ModelAndView confirmDeletationOfMultpleProjects(@PathVariable final Long restProjectId, @PathVariable final Long restApplicationId, @ModelAttribute final DeleteRestResourceCommand deleteRestResourceCommand) {
        restProjectService.deleteRestResources(restProjectId, restApplicationId, deleteRestResourceCommand.getRestResources());
        return redirect("/rest/project/" + restProjectId + "/application/" + restApplicationId);
    }



}

