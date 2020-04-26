package ee.aktors.andrei.task.service;

import ee.aktors.andrei.task.domain.Client;
import ee.aktors.andrei.task.dto.ClientDto;
import ee.aktors.andrei.task.exception.CustomException;
import ee.aktors.andrei.task.mapper.ClientMapper;
import ee.aktors.andrei.task.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Transactional
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClientDto getClientByPersonalId(Long personalId) throws CustomException {
        Optional<Client> clientOpt = clientRepository.findByPersonalId(personalId);
        Client client = clientOpt.orElseThrow(() -> {
            log.warn("getClientById request with not existed personalId");
            return new CustomException("Client with personal id " + personalId + " not found.",
                HttpStatus.NOT_FOUND);
    });
        return clientMapper.map(client);
    }

    @Transactional
    public void deleteClientByPersonalId(Long personalId) throws CustomException {
        if (!clientRepository.existsById(personalId)) {
            log.warn("deleteClientById request with not existed personalId");
            throw new CustomException("Client with personal id " + personalId + " not found.",
                    HttpStatus.NOT_FOUND);
        }
        clientRepository.deleteById(personalId);
    }

    @Transactional
    public ClientDto updateClient(ClientDto clientDto) throws CustomException {
        if (clientDto.getPersonalId() == null) {
            log.warn("updateClient request with null personalId");
            throw new CustomException("Client personal id can't be null.",
                    HttpStatus.BAD_REQUEST);
        } else {
            Client client = clientRepository.findByPersonalId(clientDto.getPersonalId()).orElseThrow(() -> {
                log.warn("updateClient request with not existed personalId");
                return new CustomException("Client with personal id " + clientDto.getPersonalId() + " not found.",
                        HttpStatus.NOT_FOUND);
            });
            Client tempClient = clientMapper.map(clientDto, client);
            return clientMapper.map(clientRepository.save(tempClient));
        }
    }

    @Transactional
    public ClientDto createClient(ClientDto clientDto) throws CustomException {
        if (clientRepository.existsById(clientDto.getPersonalId())) {
            log.warn("createClient request with existed personalId");
            throw new CustomException("Client personal id is already in use.",
                    HttpStatus.BAD_REQUEST);
        }
        Client tempClient = clientMapper.map(clientDto);
        Client client = clientRepository.save(tempClient);
        return clientMapper.map(client);
    }

}
