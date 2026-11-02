# Relatório de auditoria — Recomeça (build de teste)

## Escopo analisado

- Projeto Android nativo em Kotlin / Jetpack Compose.
- 119 entradas no ZIP original; 47 arquivos Kotlin no projeto extraído.
- 10 telas de jogos/atividades cognitivas.
- Persistência local via Room.

## Correções aplicadas nesta revisão

1. Removido o PIX fictício da tela VIP.
2. Removido o botão que liberava VIP localmente sem pagamento confirmado.
3. A tela VIP agora informa claramente que o checkout ainda não está habilitado.
4. Removido o PIN administrativo fixo `1234` e o bypass `admin`.
5. Painel administrativo desativado até existir autorização validada no backend.
6. Removidos números comerciais estáticos que pareciam métricas reais.
7. Tela inicial de acesso alterada para modo de demonstração local, sem pedir senha nem fingir autenticação online.
8. Perfil novo começa com zero dias, zero gastos informados, zero XP e sem contato telefônico inventado.
9. SOS só oferece ligação quando o usuário cadastrou um telefone.
10. Simulador financeiro identificado como cenário hipotético; removidas alegações de ausência de risco/retorno garantido.
11. Desativado backup automático do aplicativo, pois o diário pode conter dados pessoais.
12. Adicionado teste para impedir que um perfil novo receba histórico financeiro ou conquistas fictícias.
13. Adicionado workflow GitHub Actions para testes e geração de APK debug.
14. Adicionado script `build-test-apk.bat` para compilar no Windows 10 com Android SDK configurado.

## Validações realizadas neste ambiente

- XML do AndroidManifest: válido.
- Workflow YAML: parseado sem erro.
- Arquivo ZIP: integridade verificada.
- Busca estática: não restam o PIN fixo nem o código PIX simulado no Kotlin.
- Busca estática: não resta função de ativação VIP local acionável pela tela de checkout.

## Limitação importante

Este ambiente de trabalho não possui Gradle instalado nem Android SDK configurado. Portanto, **não foi possível executar a compilação Android aqui**. O workflow e o script de Windows foram adicionados para executar a compilação em um ambiente Android/Gradle real. Não considere o APK validado até o workflow terminar com sucesso e o aplicativo ser instalado/testado em um aparelho.

## Bloqueadores para lançamento comercial

- Supabase Auth e sincronização ainda precisam ser implementados.
- Checkout Mercado Pago e webhook de confirmação ainda precisam ser implementados em backend seguro.
- Direito VIP deve ser atribuído exclusivamente pelo backend após consultar o pagamento no provedor.
- Papéis administrativos precisam ser implementados e verificados no servidor.
- Ainda é necessário preparar assinatura de release, política de privacidade, termos, suporte e testes de publicação.

**Esta entrega é para demonstração/teste. Não anunciar cadastro online ou pagamento VIP ativo.**
