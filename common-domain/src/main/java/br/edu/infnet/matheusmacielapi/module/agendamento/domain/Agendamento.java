package br.edu.infnet.matheusmacielapi.module.agendamento.domain;

import br.edu.infnet.matheusmacielapi.domain.Morador;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_agendamento")
public class Agendamento {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private LocalDate dataAgendamento;

        @Column(nullable = false)
        private String status;

        @ManyToOne(optional = false)
        @JoinColumn(name = "morador_id")
        private Morador morador;

        @ManyToOne(optional = false)
        @JoinColumn(name = "recurso_id")
        private RecursoComum recurso;

        public Agendamento() {
        }

        public Agendamento(Long id, LocalDate dataAgendamento,
                           String status,
                           Morador morador,
                           RecursoComum recurso) {
            this.id = id;
            this.dataAgendamento = dataAgendamento;
            this.status = status;
            this.morador = morador;
            this.recurso = recurso;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public LocalDate getDataAgendamento() {
            return dataAgendamento;
        }

        public void setDataAgendamento(LocalDate dataAgendamento) {
            this.dataAgendamento = dataAgendamento;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Morador getMorador() {
            return morador;
        }

        public void setMorador(Morador morador) {
            this.morador = morador;
        }

        public RecursoComum getRecurso() {
            return recurso;
        }

        public void setRecurso(RecursoComum recurso) {
            this.recurso = recurso;
        }
}
