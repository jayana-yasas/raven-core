package com.example.demo.component;

import com.example.demo.dto.request.TemplateDto;
import com.example.demo.entity.Template;
import com.example.demo.repository.TemplateRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class AddTemplateComponent {

    private final TemplateRepository templateRepository;

    public void addTemplate(String traceId, Long userId, TemplateDto templateDto) {
        Template template;
        Optional<Template> templateOptional = templateRepository.findByNameAndTemplateTypeAndUserId(templateDto.getName(), templateDto.getType(), userId);
        template = templateOptional.orElseGet(Template::new);

        template.setTemplateType(templateDto.getType());
        if (templateDto.getType().equalsIgnoreCase("SMS")) {
            template.setTemplateSms(templateDto.getTemplate());
        } else if (templateDto.getType().equalsIgnoreCase("EMAIL")) {
            template.setTemplateEmail(templateDto.getTemplate());
        }
        template.setName(templateDto.getName());
        template.setUserId(userId);
        templateRepository.save(template);
    }

}
