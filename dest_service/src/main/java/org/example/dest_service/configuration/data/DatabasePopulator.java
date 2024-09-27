package org.example.dest_service.configuration.data;

import jakarta.transaction.Transactional;
import org.example.dest_service.repository.pos.CustomerPosRepository;
import org.example.dest_service.repository.pos.DocumentPaymentsRepository;
import org.example.dest_service.repository.pos.DocumentPositionsRepository;
import org.example.dest_service.repository.pos.DocumentRepository;
import org.example.dest_service.repository.webshop.CustomerWebshopRepository;
import org.example.dest_service.repository.webshop.DeliveryNotePaymentsRepository;
import org.example.dest_service.repository.webshop.DeliveryNotePositionsRepository;
import org.example.dest_service.repository.webshop.DeliveryNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

@Configuration
public class DatabasePopulator {

    @Autowired
    CustomerPosRepository customerPosRepository;
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    DocumentPositionsRepository documentPositionsRepository;
    @Autowired
    DocumentPaymentsRepository documentPaymentsRepository;

    @Autowired
    CustomerWebshopRepository customerWebshopRepository;
    @Autowired
    DeliveryNoteRepository deliveryNoteRepository;
    @Autowired
    DeliveryNotePositionsRepository deliveryNotePositionsRepository;
    @Autowired
    DeliveryNotePaymentsRepository deliveryNotePaymentsRepository;

    @EventListener
    @Transactional
    public void onApplicationShutdown(ContextClosedEvent event) {
        customerPosRepository.deleteAll();
        documentRepository.deleteAll();
        documentPositionsRepository.deleteAll();
        documentPaymentsRepository.deleteAll();

        customerWebshopRepository.deleteAll();
        deliveryNoteRepository.deleteAll();
        deliveryNotePositionsRepository.deleteAll();
        deliveryNotePaymentsRepository.deleteAll();
    }
}
