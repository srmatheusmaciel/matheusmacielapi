package br.edu.infnet.matheusmacielapi.service;

import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.domain.Unidade;
import br.edu.infnet.matheusmacielapi.domain.Veiculo;
import br.edu.infnet.matheusmacielapi.dto.MoradorRequestDTO;
import br.edu.infnet.matheusmacielapi.dto.MoradorResponseDTO;
import br.edu.infnet.matheusmacielapi.infra.exception.ResourceNotFoundException;
import br.edu.infnet.matheusmacielapi.repository.MoradorRepository;
import br.edu.infnet.matheusmacielapi.repository.UnidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MoradorService {

    private final MoradorRepository moradorRepository;
    private final UnidadeRepository unidadeRepository;
    private final VeiculoService veiculoService;

    public MoradorService(MoradorRepository moradorRepository, UnidadeRepository unidadeRepository, VeiculoService veiculoService) {
        this.moradorRepository = moradorRepository;
        this.unidadeRepository = unidadeRepository;
        this.veiculoService = veiculoService;
    }

    @Transactional(readOnly = true)
    public Collection<MoradorResponseDTO> listarTodos() {
        return moradorRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MoradorResponseDTO buscarPorId(Long id) {
        Morador morador = findMoradorById(id);
        return toResponseDTO(morador);
    }

    @Transactional(readOnly = true)
    public Collection<MoradorResponseDTO> buscarPorNome(String nome) {
        return moradorRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Collection<MoradorResponseDTO> buscarPorNomeEStatusProprietario(String nome, boolean ehProprietario) {
        return moradorRepository.findByNomeContainingIgnoreCaseAndProprietario(nome, ehProprietario).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Collection<MoradorResponseDTO> buscarPorBlocoEVeiculo(String nomeBloco, String corVeiculo) {
        return moradorRepository.findByUnidadeBlocoNomeContainingIgnoreCaseAndVeiculosCorIgnoreCase(nomeBloco, corVeiculo).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public MoradorResponseDTO salvar(MoradorRequestDTO moradorDTO) {
        Morador morador = toEntity(moradorDTO);
        morador = moradorRepository.save(morador);
        return toResponseDTO(morador);
    }

    @Transactional
    public MoradorResponseDTO atualizar(Long id, MoradorRequestDTO moradorDTO) {
        Morador moradorExistente = findMoradorById(id);

        updateEntityFromDTO(moradorExistente, moradorDTO);

        moradorExistente = moradorRepository.save(moradorExistente);
        return toResponseDTO(moradorExistente);
    }

    @Transactional
    public void excluir(Long id) {
        Morador morador = findMoradorById(id);
        moradorRepository.delete(morador);
    }

    private Morador findMoradorById(Long id) {
        return moradorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Morador não encontrado para o ID: " + id));
    }

    private MoradorResponseDTO toResponseDTO(Morador morador) {
        MoradorResponseDTO dto = new MoradorResponseDTO();
        dto.setId(morador.getId());
        dto.setNome(morador.getNome());
        dto.setEmail(morador.getEmail());
        dto.setTelefone(morador.getTelefone());
        dto.setStatusProprietario(morador.isProprietario() ? "Sim" : "Não");

        if (morador.getUnidade() != null) {
            MoradorResponseDTO.UnidadeResponseDTO unidadeDTO = new MoradorResponseDTO.UnidadeResponseDTO();
            unidadeDTO.setNumero(morador.getUnidade().getNumero());
            if (morador.getUnidade().getBloco() != null) {
                unidadeDTO.setNomeBloco(morador.getUnidade().getBloco().getNome());
            }
            dto.setUnidade(unidadeDTO);
        }
        return dto;
    }

    private Morador toEntity(MoradorRequestDTO dto) {
        Morador morador = new Morador();
        updateEntityFromDTO(morador, dto);
        return morador;
    }

    private void updateEntityFromDTO(Morador morador, MoradorRequestDTO dto) {
        morador.setNome(dto.getNome());
        morador.setDocumento(dto.getDocumento());
        morador.setTelefone(dto.getTelefone());
        morador.setEmail(dto.getEmail());
        morador.setProprietario(dto.isProprietario());
        morador.setTaxaCondominio(dto.getTaxaCondominio());

        Unidade unidade = unidadeRepository.findById(dto.getUnidadeId())
                .orElseThrow(() -> new ResourceNotFoundException("Unidade não encontrada para o ID: " + dto.getUnidadeId()));
        morador.setUnidade(unidade);
    }

    @Transactional
    public Veiculo adicionarVeiculo(Long idMorador, Veiculo veiculo) {
        Morador morador = findMoradorById(idMorador);

        Veiculo veiculoSalvo = veiculoService.salvar(veiculo);

        morador.getVeiculos().add(veiculoSalvo);
        moradorRepository.save(morador);

        return veiculoSalvo;
    }

    @Transactional
    public void excluirVeiculo(Long idMorador, Long idVeiculo) {
        Morador morador = findMoradorById(idMorador);

        Veiculo veiculoParaExcluir = morador.getVeiculos().stream()
                .filter(v -> v.getId().equals(idVeiculo))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Veículo com ID " + idVeiculo + " não encontrado para o morador com ID " + idMorador
                ));

        morador.getVeiculos().remove(veiculoParaExcluir);

        moradorRepository.save(morador);
    }
}