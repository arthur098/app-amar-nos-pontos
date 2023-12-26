package br.com.amar.nos.pontos.service;

import android.os.Environment;
import br.com.amar.nos.pontos.model.Contrato;
import br.com.amar.nos.pontos.model.Pessoa;
import br.com.amar.nos.pontos.util.CpfCnpjUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.*;
import java.util.Arrays;
import java.util.Locale;

public class ContratoService {

    private static final Font fonte = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    private static final Font fonteBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final int ALIGN_LEFT = Paragraph.ALIGN_LEFT;
    private static final int ALIGN_CENTER = Paragraph.ALIGN_CENTER;
    private static final int ALIGN_JUSTIFIED = Paragraph.ALIGN_JUSTIFIED;
    private static final String CLAUSULA_CONTRATADA = "Amanda Rodrigues Piaza e Silva, brasileira, casada, bordadeira, inscrita no CPF sob o nº 701.425.381-67, residente e domiciliada na Rua do Salmão, quadra 20, lote 25, Jardim Atlântico, em Goiânia - GO.";
    private static final String NOME_ARQUIVO = "/contrato-%s.pdf";

    public void createPdf(Contrato contrato, byte[] assinatura) {
        Pessoa pessoa = contrato.getPessoa();
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + String.format(NOME_ARQUIVO, pessoa.getNomeCompleto().trim().toLowerCase().replace(" ", "-")));
        Document document = new Document();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            PdfWriter.getInstance(document, fileOutputStream);

            document.open();

            addParagraph(document, "CONTRATO DE PRESTAÇÃO DE SERVIÇOS", fonteBold, ALIGN_CENTER);

            document.add(Chunk.NEWLINE);

            addParagraph(document, "DAS PARTES", fonteBold, ALIGN_LEFT);

            document.add(Chunk.NEWLINE);

            String contratante = montaClausulaContratante(contrato, pessoa);
            addParagraphChunks(document, ALIGN_JUSTIFIED, new Chunk("CONTRATANTE: ", fonteBold), new Chunk(contratante, fonte));

            document.add(Chunk.NEWLINE);

            addParagraphChunks(document, ALIGN_LEFT, new Chunk("CONTRATADA: ", fonteBold),
                    new Chunk(CLAUSULA_CONTRATADA, fonte));

            document.add(Chunk.NEWLINE);

            addParagraph(document, "As partes acima identificadas têm, entre si, justo e acertado o presente Contrato de Prestação de Serviços, que se regerá pelas cláusulas seguintes e pelas condições de preço, forma e termo de pagamento descritas no presente.", fonte, ALIGN_JUSTIFIED);

            document.add(Chunk.NEWLINE);

            addParagraph(document, "DO OBJETO DO CONTRATO", fonteBold, ALIGN_LEFT);

            document.add(Chunk.NEWLINE);

            addParagraphChunks(document, ALIGN_JUSTIFIED, new Chunk("Cláusula 1ª. ", fonteBold),
                    new Chunk("É objeto do presente contrato a prestação do serviço de confecção de ", fonte),
                    new Chunk(contrato.getDescricaoProduto().endsWith(".") ? contrato.getDescricaoProduto() : contrato.getDescricaoProduto().concat("."), fonte));

            document.add(Chunk.NEWLINE);

            addParagraph(document, "OBRIGAÇÕES DO CONTRATANTE", fonteBold, Paragraph.ALIGN_LEFT);

            document.add(Chunk.NEWLINE);

            addParagraphChunks(document, ALIGN_JUSTIFIED, new Chunk("Cláusula 2ª. ", fonteBold),
                    new Chunk("O ", fonte),
                    new Chunk("CONTRATANTE ", fonteBold),
                    new Chunk("deverá fornecer a "),
                    new Chunk("CONTRATADA ", fonteBold),
                    new Chunk("a CONTRATADA todas as informações necessárias à realização do serviço, devendo especificar os detalhes necessários à perfeita confecção do mesmo, e a forma de como ele deve ser entregue."));

            document.add(Chunk.NEWLINE);

            addParagraphChunks(document, ALIGN_JUSTIFIED, new Chunk("Cláusula 3ª. ", fonteBold),
                    new Chunk("O ", fonte),
                    new Chunk("CONTRATANTE ", fonteBold),
                    new Chunk("deverá efetuar o pagamento na seguinte forma e condição: um sinal de 50% no ato da assinatura do presente instrumento e o restante ao término da peça.", fonte));

            document.add(Chunk.NEWLINE);

            addParagraph(document, "OBRIGAÇÕES DA CONTRATADA", fonteBold, ALIGN_LEFT);

            document.add(Chunk.NEWLINE);

            addParagraphChunks(document, ALIGN_JUSTIFIED, new Chunk("Cláusula 4ª. ", fonteBold),
                    new Chunk("É dever da ", fonte),
                    new Chunk("CONTRATADA ", fonteBold),
                    new Chunk("oferecer ao contratante a cópia do presente instrumento, contendo todas as especificidades da prestação de serviço contratada.", fonte));

            document.add(Chunk.NEWLINE);

            addParagraph(document, "DO PREÇO E DAS CONDIÇÕES DE PAGAMENTO", fonteBold, ALIGN_LEFT);

            document.add(Chunk.NEWLINE);

            addParagraphChunks(document, ALIGN_JUSTIFIED,
                    new Chunk("Cláusula 5ª. ", fonteBold),
                    new Chunk("O presente serviço será remunerado pela quantia de R$ ", fonte),
                    new Chunk(String.format(new Locale("pt", "BR"), "%.2f", contrato.getValor()), fonte),
                    new Chunk(" referente aos serviços efetivamente prestados, devendo ser pago em PIX ou transferência bancária, ou outra forma de pagamento em que ocorra a prévia concordância de ambas as partes.", fonte));

            document.add(Chunk.NEWLINE);

            addParagraph(document, "DA ENTREGA OU RETIRADA", fonteBold, ALIGN_LEFT);

            document.add(Chunk.NEWLINE);

            addParagraphChunks(document, ALIGN_JUSTIFIED,
                    new Chunk("Cláusula 6ª. ", fonteBold),
                    new Chunk("Após a finalização da peça esta poderá ser retirada na residência da contratada ou, caso a contratante prefira que seja entregue, haverá uma taxa a ser paga pela contratante, sejam correios ou serviços de entrega terceirizada.", fonte));

            document.add(Chunk.NEWLINE);

            addParagraph(document, "DA RESCISÃO IMOTIVADA", fonteBold, ALIGN_LEFT);

            document.add(Chunk.NEWLINE);

            addParagraphChunks(document, ALIGN_JUSTIFIED, new Chunk("Cláusula 7ª. ", fonteBold),
                    new Chunk("Poderá o presente instrumento ser rescindido por qualquer uma das partes, em qualquer momento, sem que haja qualquer tipo de motivo relevante, não obstante a outra parte deverá ser avisada previamente por escrito, no prazo de 7 dias.", fonte));

            document.add(Chunk.NEWLINE);

            addParagraphChunks(document, ALIGN_JUSTIFIED, new Chunk("Cláusula 8ª. ", fonteBold),
                    new Chunk("Caso o ", fonte),
                    new Chunk("CONTRATANTE ", fonteBold),
                    new Chunk("já tenha realizado o pagamento do sinal, e mesmo assim, requisite a rescisão imotivada do presente contrato, este não será devolvido.", fonte));

            document.add(Chunk.NEWLINE);

            addParagraphChunks(document, ALIGN_JUSTIFIED, new Chunk("Cláusula 9ª. ", fonteBold),
                    new Chunk("Caso seja a ", fonte),
                    new Chunk("CONTRATADA ", fonteBold),
                    new Chunk("quem requeira a rescisão imotivada, deverá devolver a quantia que se refere aos serviços por ele não prestados ao ", fonte),
                    new Chunk("CONTRATANTE.", fonteBold));

            document.add(Chunk.NEWLINE);

            addParagraph(document, "DA ENTREGA E DAS ALTERAÇÕES", fonteBold, ALIGN_LEFT);

            document.add(Chunk.NEWLINE);

            addParagraphChunks(document, ALIGN_JUSTIFIED, new Chunk("Cláusula 10ª. ", fonteBold),
                    new Chunk("A ", fonte),
                    new Chunk("CONTRATADA ", fonteBold),
                    new Chunk("assume o compromisso de realizar o serviço e finalizar a peça até uma semana da data do casamento, de acordo com a forma estabelecida no presente contrato. Este prazo pode ser diminuído ou aumentado conforme anuência das partes.", fonte));

            document.add(Chunk.NEWLINE);

            addParagraphChunks(document, ALIGN_JUSTIFIED, new Chunk("Cláusula 11ª. ", fonteBold),
                    new Chunk("Fica compactuado a total inexistência de vínculo trabalhista entre as partes contratantes, excluindo as obrigações previdenciárias e os encargos sociais.", fonte));

            document.add(Chunk.NEWLINE);

            addParagraphChunks(document, ALIGN_JUSTIFIED, new Chunk("Cláusula 12ª. ", fonteBold),
                    new Chunk("A contratante tem o direito de realizar até DUAS alterações na peça após a aprovação da arte. Para mais alterações será cobrado um valor de R$10,00 por alteração. (CLÁUSULA SOMENTE PARA BORDADOS E FLÂMULAS)", fonte));

            document.add(Chunk.NEWLINE);

            addParagraph(document, "DO FORO", fonteBold, ALIGN_LEFT);

            document.add(Chunk.NEWLINE);

            addParagraphChunks(document, ALIGN_JUSTIFIED, new Chunk("Cláusula 13ª. ", fonteBold),
                    new Chunk("Para dirimir quaisquer controvérsias oriundas do presente contrato, as partes elegem o foro da comarca de Goiânia.", fonte));

            document.add(Chunk.NEWLINE);

            addParagraph(document, "Por estarem assim justos e contratados, firmam o presente instrumento, em duas vias de igual teor, juntamente com 2(duas) testemunhas.", fonte, ALIGN_JUSTIFIED);

            document.add(Chunk.NEWLINE);

            PdfPTable pdfPTable = new PdfPTable(new float[]{ 1 });

            PdfPCell cell = new PdfPCell();
            cell.setBorder(PdfPCell.BOTTOM);
            cell.setBorderColorBottom(BaseColor.BLACK);
            pdfPTable.addCell(cell);
            pdfPTable.setWidthPercentage(100);

            document.add(pdfPTable);

            addParagraphChunks(document, ALIGN_LEFT,
                    new Chunk("(".concat(pessoa.getNomeCompleto()).concat(")"), fonte),
                    new Chunk(" - CONTRATANTE"));

            document.add(Chunk.NEWLINE);

            Image image = getImage(assinatura);
            image.scaleAbsoluteHeight(100);
            image.scaleAbsoluteWidth(60);
            document.add(new Chunk(image, 0, 0, true));

            document.add(pdfPTable);
            addParagraph(document, "(Amanda Rodrigues Piaza e Silva) - CONTRATADA", fonte, ALIGN_LEFT);

            document.add(Chunk.NEWLINE);

            document.add(pdfPTable);
            addParagraph(document, "(Testemunha)", fonte, ALIGN_LEFT);

            document.add(Chunk.NEWLINE);

            document.add(pdfPTable);
            addParagraph(document, "(Testemunha)", fonte, ALIGN_LEFT);

        } catch (FileNotFoundException|DocumentException e) {
            throw new RuntimeException(e);
        } finally {
            document.close();
        }
    }

    private String montaClausulaContratante(Contrato contrato, Pessoa pessoa) {
        return new StringBuilder(pessoa.getNomeCompleto())
                .append(", ")
                .append(pessoa.getNacionalidade())
                .append(", ")
                .append(pessoa.getEstadoCivil().getDescricao())
                .append(", ")
                .append(pessoa.getProfissao())
                .append(", ")
                .append("inscrito(a) no CPF sob o nº ")
                .append(CpfCnpjUtil.formatar(pessoa.getCpfCnpj()))
                .append(", residente e domiciliado(a) na ")
                .append(contrato.getEndereco().getEnderecoFormatado())
                .append(".").toString();
    }

    public void addParagraph(Document document, String texto, Font fonte, Integer alinhamento) throws DocumentException {
        Paragraph justificado = new Paragraph(texto, fonte);

        if(alinhamento != null) {
            justificado.setAlignment(alinhamento);
        }

        document.add(justificado);
    }

    public void addParagraphChunks(Document document, Integer alinhamento, Chunk... chunks) throws DocumentException {
        Paragraph justificado = new Paragraph();
        justificado.addAll(Arrays.asList(chunks));

        if(alinhamento != null) {
            justificado.setAlignment(alinhamento);
        }

        document.add(justificado);
    }

    public Image getImage(byte[] imagem) {
        try {
            return Image.getInstance(imagem);
        } catch (IOException | DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
