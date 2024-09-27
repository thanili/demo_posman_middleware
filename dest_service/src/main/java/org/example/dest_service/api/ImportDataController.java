package org.example.dest_service.api;

import org.example.api.dto.receipt.ReceiptDto;
import org.example.api.dto.ResponseDto;
import org.example.dest_service.service.DataTransformationAndImportService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling import operations.
 */
@RestController
@RequestMapping("/api/import")
public class ImportDataController {
    private final Logger logger;
    private final DataTransformationAndImportService dataTransformationAndImportService;

    @Value("${org.example.service.webshopid}") //org.example.service.webshopid
    private String webshopDeviceNumber;

    @Autowired
    public ImportDataController(Logger logger, DataTransformationAndImportService dataTransformationAndImportService) {
        this.logger = logger;
        this.dataTransformationAndImportService = dataTransformationAndImportService;
    }

    /**
     * Endpoint for importing receipt data.
     *
     * @param receiptDto the receipt data transfer object
     * @return ResponseEntity containing the response data transfer object and HTTP status
     */
    @PostMapping("/receipt")
    public ResponseEntity<ResponseDto> importReceipt(@RequestBody ReceiptDto receiptDto) {
        logger.info("ImportDataController importReceipt" + receiptDto.toString());

        if(receiptDto == null) {
            logger.info("No data in the request");
            return new ResponseEntity<>(new ResponseDto(1,"No data in the request"), HttpStatus.BAD_REQUEST);
        }

        String deviceNumber = String.valueOf(receiptDto.getDeviceNumber());
        if (webshopDeviceNumber.equals(deviceNumber)) {
            try {
                dataTransformationAndImportService.transformReceiptDataAndSaveWebshop(receiptDto);
                logger.info("Receipt imported successfully into destination_database_01");
                return new ResponseEntity<>(new ResponseDto(0, "Receipt imported successfully into destination_database_01"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponseDto(1,"Error during receipt import"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            try {
                dataTransformationAndImportService.transformReceiptDataAndSavePos(receiptDto);
                logger.info("Receipt imported successfully into destination_database_02");
                return new ResponseEntity<>(new ResponseDto(0, "Receipt imported successfully into destination_database_02"), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new ResponseDto(1,"Error during receipt import"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Import Controller is working");
    }
}
