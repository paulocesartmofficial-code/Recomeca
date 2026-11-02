package com.example.data.seed

import com.example.data.model.EducationalArticle

object EducationalArticlesData {
    val articles: List<EducationalArticle> = listOf(
        // Categoria 1: Probabilidades e Matemática
        EducationalArticle(
            id = "prob_1",
            category = "Matemática e Probabilidades",
            title = "A Vantagem Matemática da Casa",
            readTimeMinutes = 4,
            summary = "Entenda por que as probabilidades em jogos de azar são matematicamente desenhadas para que o apostador sempre perca no longo prazo.",
            introduction = "Todo cassino ou casa de aposta opera com base no conceito do RTP (Return to Player) e margem da casa (House Edge).",
            mainText = "Mesmo que você tenha ganhos temporários, a lei dos grandes números garante que quanto mais rodadas você joga, mais próximo o resultado final fica do retorno programado matematicamente — que é sempre menor que 100%. Em slots e roletas, a margem varia de 3% a mais de 15%. Em apostas esportivas, o 'juice' ou 'vig' garante o lucro da banca independentemente de quem vença.",
            realExample = "Carlos ganhou R$ 1.200 em uma semana jogando roleta. Sentindo-se confiante, continuou apostando nos meses seguintes. Três meses depois, o somatório de suas apostas havia gerado uma perda acumulada de R$ 9.400, exatamente dentro da expectativa estatística da casa.",
            conclusion = "Não existe sorte consistente contra a probabilidade pura. A única forma matemática de garantir que você não perderá é não jogar.",
            recommendedAction = "Faça a simulação na Calculadora Financeira e visualize quanto dinheiro retorna ao seu bolso quando você para de alimentar essa margem."
        ),
        EducationalArticle(
            id = "prob_2",
            category = "Matemática e Probabilidades",
            title = "A Falácia do Apostador",
            readTimeMinutes = 3,
            summary = "A ilusão de que eventos passados independentes influenciam o próximo resultado de um jogo.",
            introduction = "A 'Falácia de Monte Carlo' ocorreu em 1913, quando uma roleta caiu no preto 26 vezes seguidas e apostadores perderam fortunas apostando no vermelho.",
            mainText = "Um evento aleatório não tem memória. Se uma moeda dá cara cinco vezes seguidas, a chance de dar cara na sexta vez continua sendo rigorosamente 50%. Em apostas, acreditar que 'já perdi muito, agora a sorte tem que virar' é uma das armadilhas cognitivas mais destrutivas.",
            realExample = "Marcos apostou 8 vezes na mesma cor porque achava 'impossível' que ela não saísse. Ele dobrou a aposta a cada rodada e perdeu todo o salário do mês em 12 minutos.",
            conclusion = "O algoritmo ou a roleta não 'deve' nada a ninguém. Cada rodada é independente e desfavorável.",
            recommendedAction = "Quando sentir a tentação de 'recuperar agora', abra o SOS e ative a pausa de 5 minutos."
        ),
        EducationalArticle(
            id = "prob_3",
            category = "Matemática e Probabilidades",
            title = "O Retorno ao Jogador (RTP) Explicado",
            readTimeMinutes = 4,
            summary = "Por que um RTP de 95% não significa que você só perderá 5% do seu depósito.",
            introduction = "Muitas plataformas anunciam jogos com RTP de 96% ou mais, criando uma ilusão de segurança financeira.",
            mainText = "O RTP não se aplica ao valor que você depositou, mas sim ao volume total girado. Se você deposita R$ 100 e faz 100 apostas de R$ 10 (girando R$ 1.000), uma perda esperada de 5% sobre R$ 1.000 equivale a R$ 50 perdidos — isto é, metade do seu saldo original evaporou em uma hora.",
            realExample = "Ao girar o saldo diversas vezes para tentar cumprir os requisitos de um bônus, Juliana viu seus R$ 200 virarem zero mesmo jogando em máquinas de 'alto RTP'.",
            conclusion = "O volume de giros multiplica as perdas rapidamente até o saldo chegar a zero.",
            recommendedAction = "Leia o artigo sobre Mecanismos de Recompensa para entender a pressa química gerada por esses giros."
        ),
        EducationalArticle(
            id = "prob_4",
            category = "Matemática e Probabilidades",
            title = "A Ilusão do Quase Ganho (Near Miss)",
            readTimeMinutes = 3,
            summary = "Como as máquinas são programadas para mostrar resultados muito próximos da vitória para manter você jogando.",
            introduction = "Dois símbolos idênticos e o terceiro símbolo quase parando na linha certa: acidente ou engenharia comportamental?",
            mainText = "Estudos de neuroimagem mostram que um 'quase ganho' ativa no cérebro as mesmas áreas dopaminérgicas de uma vitória real, mas com um aditivo: a sensação de que a vitória é iminente. Nos caça-níqueis e jogos virtuais, a frequência de quase-ganhos é artificialmente manipulada.",
            realExample = "Renato ficava eufórico quando faltava apenas um número para a quina ou quando o multiplicador parava no 9.9x. Ele sentia que estava 'pegando o jeito', quando na verdade estava apenas sendo estimulado por um truque de interface.",
            conclusion = "Quase ganhar é exatamente o mesmo que perder: o dinheiro foi debitado da sua conta.",
            recommendedAction = "Pratique um dos nossos jogos de raciocínio real na Central de Jogos para canalizar seu cérebro em desafios legítimos."
        ),

        // Categoria 2: O Mito de Recuperar o Dinheiro
        EducationalArticle(
            id = "mito_1",
            category = "O Mito de Recuperar",
            title = "A Armadilha do 'Chasing Losses'",
            readTimeMinutes = 4,
            summary = "Como a tentativa desesperada de recuperar o prejuízo transforma perdas controláveis em crises profundas.",
            introduction = "A frase mais perigosa na mente de quem aposta é: 'Só preciso recuperar o que perdi hoje e nunca mais jogo'.",
            mainText = "Quando você perde dinheiro, o cérebro entra em estado de estresse agudo e aversão à perda. A amígdala cerebral assume o controle, reduzindo a capacidade do córtex pré-frontal de pensar com racionalidade. O resultado é aumentar as apostas e assumir riscos impensáveis.",
            realExample = "Lucas perdeu R$ 300 que eram para uma conta. Desesperado, depositou mais R$ 500 para tentar recuperar. No final da madrugada, o prejuízo passava de R$ 3.500.",
            conclusion = "O dinheiro que ficou para trás já se foi. Cada real não apostado hoje é um real conquistado e preservado para o seu futuro.",
            recommendedAction = "Escreva no seu Diário Pessoal uma declaração formal de aceitação das perdas passadas."
        ),
        EducationalArticle(
            id = "mito_2",
            category = "O Mito de Recuperar",
            title = "O Custo Afundado (Sunk Cost Fallacy)",
            readTimeMinutes = 3,
            summary = "Aprenda a desapegar do investimento passado para não comprometer o presente e o futuro.",
            introduction = "Por que insistimos em algo só porque já gastamos muito tempo ou dinheiro nisso?",
            mainText = "A falácia do custo afundado faz as pessoas pensarem que desistir de apostar significa 'admitir a derrota'. Na verdade, interromper as apostas é a decisão mais estratégica que você pode tomar, pois estanca a sangria financeira e protege tudo o que você ainda tem.",
            realExample = "Paula dizia: 'Já perdi 20 mil, não posso parar agora sem reaver uma parte'. Quando finalmente aceitou o custo afundado, conseguiu quitar as dívidas em 14 meses.",
            conclusion = "Parar de apostar não é perder: é parar de sangrar.",
            recommendedAction = "Defina uma meta na aba Metas Financeiras para começar a poupar ativamente."
        ),
        EducationalArticle(
            id = "mito_3",
            category = "O Mito de Recuperar",
            title = "A Grande Vitória que Nunca Mantém Você a Salvo",
            readTimeMinutes = 4,
            summary = "Descubra por que mesmo quando os apostadores ganham uma grande bolada, quase sempre devolvem tudo com juros.",
            introduction = "A maior mentira contada aos apostadores é que uma grande vitória resolveria seus problemas.",
            mainText = "Ganhos extraordinários geram picos maciços de dopamina e uma sensação ilusória de onipotência. Para quem desenvolveu o hábito compulsivo, o dinheiro ganho não vai para a poupança: ele se torna apenas combustível para apostas ainda maiores até que tudo seja devolvido.",
            realExample = "Eduardo ganhou R$ 25.000 em uma aposta acumulada. Poderia ter quitado seu carro. Em menos de 20 dias, devolveu os R$ 25.000 e acumulou mais R$ 8.000 de cheque especial.",
            conclusion = "A aposta nunca sacia; ela apenas aumenta a sede de adrenalina.",
            recommendedAction = "Visualize seu progresso de dias limpos na aba Jornada para valorizar a paz mental."
        ),

        // Categoria 3: Mecanismos de Recompensa & Neurobiologia
        EducationalArticle(
            id = "neuro_1",
            category = "Neurobiologia do Hábito",
            title = "Dopamina e Reforço Intermitente",
            readTimeMinutes = 5,
            summary = "Entenda como a incerteza do resultado sequestra o circuito de recompensa do cérebro.",
            introduction = "O psicólogo B.F. Skinner descobriu que o padrão mais viciante para qualquer cérebro é a recompensa imprevisível.",
            mainText = "Se uma máquina sempre desse R$ 10 ou nunca desse nada, o cérebro perderia o interesse rapidamente. É a incerteza — o 'talvez agora dê certo' — que dispara enxurradas de dopamina no cérebro. Esse é o mesmo mecanismo que mantém as pessoas presas a feeds infinitos e jogos de azar.",
            realExample = "Experimentos com animais mostram que eles pressionam a alavanca freneticamente até a exaustão quando a comida sai de forma aleatória.",
            conclusion = "Sua dificuldade em parar não é falta de caráter; é uma reação biológica a um sistema desenhado especificamente para manipular seus neurotransmissores.",
            recommendedAction = "Faça a Respiração Guiada por 3 minutos para desacelerar o sistema nervoso autônomo."
        ),
        EducationalArticle(
            id = "neuro_2",
            category = "Neurobiologia do Hábito",
            title = "A Dessensibilização dos Prazeres Simples",
            readTimeMinutes = 4,
            summary = "Por que atividades normais parecem sem graça quando você está acostumado com os picos de dopamina das apostas.",
            introduction = "Assistir a um filme, ler um livro ou passear com a família começaram a parecer monótonos para você?",
            mainText = "Quando o cérebro é bombardeado repetidamente por estímulos hiperintensos de apostas, ele reduz o número de receptores de dopamina para se proteger. Esse processo é chamado de 'downregulation'. Como consequência, as coisas simples da vida perdem temporariamente o brilho.",
            realExample = "Felipe sentia que a vida estava 'cinza' nos primeiros dias após parar. Com 21 dias de abstinência, o sabor da comida e o prazer de conversar voltaram a ser vívidos.",
            conclusion = "A sensibilidade ao prazer cotidiano volta com o tempo e com a neuroplasticidade.",
            recommendedAction = "Registre no Diário Pessoal 3 pequenas coisas pelas quais você é grato hoje."
        ),
        EducationalArticle(
            id = "neuro_3",
            category = "Neurobiologia do Hábito",
            title = "A Onda do Impulso: O Conceito de Urge Surfing",
            readTimeMinutes = 4,
            summary = "Aprenda a surfar as ondas de vontade sem ser derrubado por elas.",
            introduction = "A vontade de apostar parece infinita quando surge, mas na realidade dura entre 5 a 15 minutos.",
            mainText = "O psicólogo Alan Marlatt cunhou o termo 'Urge Surfing'. Uma vontade intensa é como uma onda do oceano: ela começa suave, atinge um pico de intensidade e, se você não ceder nem lutar contra ela desesperadamente, ela inevitavelmente quebra e se dissipa.",
            realExample = "Em vez de correr para o aplicativo de apostas, Tiago abriu o botão SOS do Recomeça e respirou fundo até o temporizador de 5 minutos zerar. A vontade reduziu em 80%.",
            conclusion = "Você não precisa fazer a vontade sumir imediatamente; basta esperar a onda descer.",
            recommendedAction = "Experimente a ferramenta SOS do Recomeça na próxima vez que o impulso surgir."
        ),

        // Categoria 4: Organização Financeira
        EducationalArticle(
            id = "fin_1",
            category = "Finanças e Reconstrução",
            title = "O Primeiro Passo: Mapeamento Real das Dívidas",
            readTimeMinutes = 5,
            summary = "Como encarar os números de frente sem pânico e traçar um plano de alívio progressivo.",
            introduction = "O medo de ver o saldo bancário faz muitos apostadores viverem em um estado de fuga constante.",
            mainText = "O primeiro ato de coragem é colocar em uma folha de papel todos os débitos, taxas de juros e datas de vencimento. As dívidas parecem monstros infinitos na imaginação, mas quando escritas tornam-se problemas matemáticos finitos e solucionáveis.",
            realExample = "Roberta tinha vergonha de somar suas faturas. Quando somou, viu que devia R$ 11.200. Com um plano de contenção, renegociou o montante por R$ 6.800 para pagar em 18 vezes.",
            conclusion = "A clareza financeira remove a ansiedade do desconhecido.",
            recommendedAction = "Cadastre sua principal dívida como meta prioritária na aba Metas Financeiras."
        ),
        EducationalArticle(
            id = "fin_2",
            category = "Finanças e Reconstrução",
            title = "A Regra das Barreiras Financeiras",
            readTimeMinutes = 4,
            summary = "Como criar obstáculos deliberados entre o seu dinheiro e as plataformas de aposta.",
            introduction = "A força de vontade é um recurso limitado; o ambiente preparado é o que garante o sucesso.",
            mainText = "Reduza os limites de PIX noturno nos aplicativos de banco. Desative cartões de crédito virtuais. Se necessário, transfira temporariamente a administração financeira diária para um familiar ou cônjuge de confiança enquanto seu equilíbrio emocional é restabelecido.",
            realExample = "Danilo reduziu seu limite de transferência instantânea para R$ 50 no horário noturno. Três vezes ele tentou apostar de madrugada e a barreira técnica o impediu a tempo de respirar.",
            conclusion = "Facilitar o comportamento saudável e dificultar o comportamento prejudicial é a regra de ouro.",
            recommendedAction = "Acesse agora seu aplicativo bancário e ajuste os limites de segurança."
        ),
        EducationalArticle(
            id = "fin_3",
            category = "Finanças e Reconstrução",
            title = "Construindo Sua Reserva de Tranquilidade",
            readTimeMinutes = 4,
            summary = "Por que uma pequena reserva de R$ 500 ou R$ 1.000 muda completamente sua segurança psicológica.",
            introduction = "Quem aposta geralmente vive no limite do cheque especial, qualquer imprevisto vira desespero.",
            mainText = "Ter uma quantia guardada, mesmo modesta, quebra o ciclo de achar que 'precisa apostar para pagar o conserto do carro ou o remédio da farmácia'. Essa reserva deve ficar em um banco separado ou investimento de liquidez diária sem cartão fácil.",
            realExample = "Ao preservar R$ 40 por semana que antes iam para apostas esportivas, Amanda acumulou R$ 960 em seis meses. Pela primeira vez em quatro anos, dormiu em paz.",
            conclusion = "A tranquilidade vale infinitamente mais que qualquer bilhete premiado.",
            recommendedAction = "Calcule na Calculadora do app o impacto de poupar seu valor médio apostado."
        ),

        // Categoria 5: Identificação de Gatilhos
        EducationalArticle(
            id = "gatilho_1",
            category = "Gatilhos e Comportamento",
            title = "O Método HALT: Fome, Raiva, Solidão e Cansaço",
            readTimeMinutes = 4,
            summary = "Conheça o acrônimo clássico que explica quando estamos mais vulneráveis a recaídas.",
            introduction = "A maioria das decisões impulsivas não nasce do nada: nasce de necessidades fisiológicas e emocionais não atendidas.",
            mainText = "HALT vem do inglês: Hungry (Faminto), Angry (Irritado), Lonely (Solitário) e Tired (Cansado). Quando estamos em um desses estados, a capacidade de autorregulação cai drasticamente. Antes de ceder a um impulso, pergunte-se: qual dessas quatro carências eu realmente estou sentindo?",
            realExample = "Bruno percebeu que 90% das suas apostas ocorriam domingo à noite, quando estava solitário e ansioso com o trabalho da segunda-feira. Ao marcar futebol com amigos, o gatilho perdeu força.",
            conclusion = "Cuidar do seu corpo e do seu descanso é parte essencial da sua recuperação.",
            recommendedAction = "Faça seu Check-in diário no app e identifique como está seu nível de humor hoje."
        ),
        EducationalArticle(
            id = "gatilho_2",
            category = "Gatilhos e Comportamento",
            title = "Gatilhos Visuais e Notificações de Celular",
            readTimeMinutes = 3,
            summary = "Como limpar seu ambiente digital de estímulos que acionam a compulsão.",
            introduction = "Grupos de Telegram de 'sinais', e-mails promocionais e influenciadores de apostas nas redes sociais.",
            mainText = "O cérebro responde a pistas ambientais. Ver uma notificação de 'bônus de depósito' ativa o gatilho mesmo que você estivesse bem minutos antes. Desinscreva-se de newsletters, saia de canais de palpites e silencie termos relacionados a apostas nas redes sociais.",
            realExample = "Ao desinstalar dois aplicativos de mídia social e silenciar palavras-chave, Letícia reduziu os pensamentos intrusivos em mais de 70% na primeira semana.",
            conclusion = "Você não pode controlar todas as tentações do mundo, mas pode blindar seu celular.",
            recommendedAction = "Faça uma faxina nos seus grupos de mensagens e desative notificações de redes sociais."
        ),
        EducationalArticle(
            id = "gatilho_3",
            category = "Gatilhos e Comportamento",
            title = "O Tédio como Inimigo Oculto",
            readTimeMinutes = 4,
            summary = "Aprenda a preencher as lacunas de tempo com atividades estimulantes e prazerosas.",
            introduction = "Muitas pessoas não apostavam apenas por dinheiro, mas para preencher horas vazias.",
            mainText = "A aposta ocupava tempo mental, planejamento e navegação. Ao parar, surge um vácuo. Se esse tempo não for preenchido com hobbies, exercícios físicos, estudos ou jogos cognitivos saudáveis, a mente tende a buscar o antigo hábito pelo simples incômodo do vazio.",
            realExample = "Gabriel passou a dedicar 30 minutos diários aos desafios de Sudoku e Xadrez do Recomeça e começou a caminhar no fim da tarde. O tédio que o levava a apostar desapareceu.",
            conclusion = "Recuperar-se não é só parar de apostar; é construir uma vida onde não faça sentido apostar.",
            recommendedAction = "Visite a Central de Jogos e jogue uma partida completa de Sudoku ou Caça-Palavras."
        ),

        // Categoria 6: Família e Relacionamentos
        EducationalArticle(
            id = "fam_1",
            category = "Relacionamentos e Apoio",
            title = "Como Ter a Primeira Conversa com Quem Você Ama",
            readTimeMinutes = 5,
            summary = "Orientações práticas para abrir o jogo com sinceridade, responsabilidade e sem vitimização.",
            introduction = "O peso do segredo é muitas vezes mais doloroso do que o próprio prejuízo financeiro.",
            mainText = "Manter o hábito em segredo consome uma energia psíquica gigantesca e corrói os laços de confiança. Ao conversar, escolha um momento calmo, assuma a responsabilidade sem culpar terceiros e foque no plano de ação para mudar. Deixe claro que você está buscando ajuda ativa.",
            realExample = "Sérgio chamou sua esposa e explicou a situação com clareza, mostrando o aplicativo Recomeça e o extrato bancário. Embora houvesse tristeza inicial, o alívio de não mentir mais restaurou a parceria do casal.",
            conclusion = "A verdade liberta e cria o alicerce para a reconstrução do respeito mútuo.",
            recommendedAction = "Cadastre o telefone de uma pessoa de confiança no seu perfil do Recomeça."
        ),
        EducationalArticle(
            id = "fam_2",
            category = "Relacionamentos e Apoio",
            title = "Reconstruindo a Confiança Passo a Passo",
            readTimeMinutes = 4,
            summary = "Entenda que palavras não bastam: a confiança se reconquista através da consistência diária.",
            introduction = "Depois da revelação, é natural que familiares fiquem desconfiados ou façam perguntas constantes.",
            mainText = "A desconfiança da família não é punição: é medo de que você se machuque novamente. Não fique na defensiva quando pedirem para ver contas ou perguntarem onde você esteve. A transparência radical e a serenidade ao longo dos meses são os únicos remédios reais.",
            realExample = "Mariana aceitou mostrar voluntariamente suas finanças todo final de mês para o irmão. Após seis meses de total transparência, a desconfiança deu lugar à admiração.",
            conclusion = "Confiança se perde em segundos e se reconstrói em dias consistentes.",
            recommendedAction = "Compartilhe uma vitória de marco (ex: 30 dias) com quem torce por você."
        ),
        EducationalArticle(
            id = "fam_3",
            category = "Relacionamentos e Apoio",
            title = "O Papel dos Grupos de Mútuo Apoio",
            readTimeMinutes = 4,
            summary = "Por que conversar com pessoas que viveram exatamente a mesma dor acelera sua superação.",
            introduction = "Muitas vezes familiares não compreendem a mecânica da compulsão; outros em recuperação sim.",
            mainText = "Grupos como Jogadores Anônimos (JA) reúnem milhares de pessoas no Brasil e no mundo. Não há julgamento, vergonha ou cobranças. Ouvir histórias de quem esteve no fundo do poço e reconstruiu a vida traz uma esperança tangível que nenhum conselho teórico consegue igualar.",
            realExample = "Cláudio achava que era o único profissional bem-sucedido que havia caído no vício das apostas. Na primeira reunião online do JA, encontrou médicos, engenheiros e professores na mesma jornada.",
            conclusion = "Você nunca está sozinho. Há uma rede inteira pronta para estender a mão.",
            recommendedAction = "Consulte os links e contatos de apoio no botão SOS do aplicativo."
        ),

        // Categoria 7: Bloqueio e Autoexclusão
        EducationalArticle(
            id = "bloq_1",
            category = "Ferramentas de Proteção",
            title = "Como Funciona a Autoexclusão em Plataformas",
            readTimeMinutes = 4,
            summary = "O direito legal do usuário de solicitar bloqueio definitivo e irrevogável de sua conta.",
            introduction = "Todas as casas regulamentadas são obrigadas a fornecer mecanismos de autoexclusão definitiva.",
            mainText = "A autoexclusão impede que a plataforma aceite novos depósitos, envie e-mails ou permita reabertura de conta durante o prazo estipulado (que pode ser vitalício). Não opte por 'pausa de 24 horas'; escolha o encerramento permanente por motivo de jogo responsável.",
            realExample = "Arthur enviou mensagem de autoexclusão com encerramento imediato de cadastro para as 4 casas que usava. Isso eliminou o acesso no momento em que teve uma recaída emocional.",
            conclusion = "Fechar as portas atrás de você garante que você não volte quando a mente vacilar.",
            recommendedAction = "Liste as plataformas em que já teve cadastro e acione a autoexclusão hoje mesmo."
        ),
        EducationalArticle(
            id = "bloq_2",
            category = "Ferramentas de Proteção",
            title = "Softwares de Bloqueio de Conteúdo e DNS",
            readTimeMinutes = 4,
            summary = "Ferramentas tecnológicas para bloquear sites de apostas em computadores e celulares.",
            introduction = "Você pode usar filtros no nível de rede e de dispositivo para impedir o carregamento de sites de jogos.",
            mainText = "Softwares como BetBlocker, Gamban e configurações de DNS seguro (como NextDNS com filtro de Gambling ativado) impedem o acesso a milhares de sites e aplicativos em milissegundos. Ao colocar uma senha administrada por outra pessoa, o bloqueio se torna à prova de impulsos.",
            realExample = "Ao ativar o NextDNS em seu roteador residencial com bloqueio de apostas, Rogério protegeu não apenas o celular como também seu tablet e computador.",
            conclusion = "A tecnologia que serviu para atraí-lo pode ser usada para protegê-lo.",
            recommendedAction = "Revise as permissões e extensões instaladas no seu navegador."
        ),
        EducationalArticle(
            id = "bloq_3",
            category = "Ferramentas de Proteção",
            title = "Bloqueio de Pagamentos e Medidas Práticas",
            readTimeMinutes = 3,
            summary = "Como solicitar o bloqueio de transações para jogos de azar junto ao seu banco.",
            introduction = "Vários bancos e instituições de pagamento já oferecem bloqueio nativo para transações de apostas esportivas e cassinos.",
            mainText = "Contate o suporte do seu banco ou consulte as configurações do cartão para habilitar a trava de transações para a categoria 'Jogos e Loterias'. Com essa trava ativada, qualquer tentativa de depósito é recusada automaticamente na origem.",
            realExample = "Helena ativou a trava de transações de entretenimento/apostas no app do banco e evitou duas recaídas durante finais de campeonato de futebol.",
            conclusion = "Menos meios de pagamento significam mais tempo para a razão retomar o comando.",
            recommendedAction = "Verifique nas configurações do seu banco se a opção de bloqueio de apostas está disponível."
        ),

        // Categoria 8: Saúde Mental e Ajuda Especializada
        EducationalArticle(
            id = "saude_1",
            category = "Saúde Mental e Profissionais",
            title = "Quando Procurar Terapia Cognitivo-Comportamental",
            readTimeMinutes = 5,
            summary = "A abordagem padrão-ouro para reestruturação de pensamentos automáticos e comportamentos compulsivos.",
            introduction = "A Terapia Cognitivo-Comportamental (TCC) é amplamente reconhecida pela comunidade médica para o tratamento do transtorno do jogo.",
            mainText = "O psicólogo especializado em TCC ajuda a mapear os pensamentos disfuncionais que antecedem a aposta ('só hoje', 'agora vai dar certo') e treina respostas comportamentais alternativas. Não hesite em buscar apoio de um profissional de saúde mental registrado no CRP.",
            realExample = "Em 8 sessões de TCC, André aprendeu a identificar que a aposta era uma válvula de escape para o estresse no trabalho e desenvolveu formas saudáveis de descompressão.",
            conclusion = "Pedir ajuda profissional é sinal de força, sabedoria e compromisso consigo mesmo.",
            recommendedAction = "Acesse a seção SOS para ver serviços gratuitos disponíveis como o CAPS do SUS."
        ),
        EducationalArticle(
            id = "saude_2",
            category = "Saúde Mental e Profissionais",
            title = "O Papel da Psiquiatria no Tratamento",
            readTimeMinutes = 4,
            summary = "Entenda a comorbidade frequente entre transtorno do jogo, ansiedade e depressão.",
            introduction = "Muitas vezes a aposta compulsiva é uma tentativa inconsciente de automedicar um quadro de ansiedade ou depressão subjacente.",
            mainText = "O médico psiquiatra avalia se há desequilíbrios químicos que facilitam a impulsividade. Em muitos casos, tratar a ansiedade ou o déficit de atenção associado reduz drasticamente a necessidade de buscar sensações extremas nas apostas.",
            realExample = "Após consultar um psiquiatra e tratar a insônia crônica e a ansiedade generalizada, Priscila relatou que a compulsão por apostas perdeu 90% da sua força invasiva.",
            conclusion = "O cuidado integral do corpo e da mente é o caminho mais sólido para a sobriedade.",
            recommendedAction = "Converse abertamente com um médico se você sente sintomas depressivos ou ansiedade constante."
        ),
        EducationalArticle(
            id = "saude_3",
            category = "Saúde Mental e Profissionais",
            title = "O SUS e os Centros de Atenção Psicossocial (CAPS)",
            readTimeMinutes = 4,
            summary = "Acesso 100% gratuito e público a psicólogos, psiquiatras e grupos de apoio em todo o Brasil.",
            introduction = "O Sistema Único de Saúde brasileiro possui unidades especializadas no acolhimento de transtornos por uso de substâncias e dependências comportamentais.",
            mainText = "Os CAPS (especialmente o CAPS AD - Álcool e Drogas / Adições) contam com equipes multidisciplinares compostas por médicos, terapeutas ocupacionais, psicólogos e assistentes sociais. O atendimento é de portas abertas, sem necessidade de encaminhamento prévio.",
            realExample = "Sem condições financeiras para terapia particular, Fernando procurou o CAPS de seu bairro e foi acolhido na mesma semana por uma equipe dedicada e humana.",
            conclusion = "A saúde pública brasileira tem portas abertas para você recomeçar.",
            recommendedAction = "Descubra qual é a unidade de CAPS mais próxima da sua residência na aba SOS."
        ),

        // Categoria 9: Filosofia de Vida e Novos Hábitos
        EducationalArticle(
            id = "vida_1",
            category = "Novos Hábitos e Propósito",
            title = "O Princípio da Substituição de Hábitos",
            readTimeMinutes = 4,
            summary = "Você não pode apenas eliminar um hábito; você precisa substituí-lo por outro mais enriquecedor.",
            introduction = "Como ensina Charles Duhigg em 'O Poder do Hábito', a deixa e a recompensa precisam ser redirecionadas.",
            mainText = "Se a sua deixa era a solidão à noite e a recompensa era a adrenalina, substituir o celular por nada gera sofrimento. Mas substituir por uma atividade física intensa, leitura imersiva ou aprendizado de um instrumento gera dopamina saudável e cansaço físico restaurador.",
            realExample = "Daniel começou a correr 5 km toda vez que sentia inquietação. Em quatro meses, participou de sua primeira prova de rua e nunca mais apostou.",
            conclusion = "Mude a rotina mantendo a busca por evolução e saúde.",
            recommendedAction = "Defina hoje mesmo uma nova atividade física ou intelectual na sua rotina."
        ),
        EducationalArticle(
            id = "vida_2",
            category = "Novos Hábitos e Propósito",
            title = "Como Lidar com uma Recaída sem se Destruir",
            readTimeMinutes = 5,
            summary = "Uma recaída é um tropeço, não o cancelamento de todo o progresso que você já conquistou.",
            introduction = "Existe uma enorme diferença entre um deslize (lapso) e o abandono completo da recuperação.",
            mainText = "O 'Efeito de Violação da Abstinência' ocorre quando a pessoa tropeça e pensa: 'Já estraguei tudo, agora vou apostar tudo o que tenho'. Não caia nessa armadilha mental. Se você ficou 60 dias sem apostar e apostou hoje, você ainda teve 60 dias de vitória e economia real na sua vida.",
            realExample = "Rodrigo teve uma recaída de R$ 50 após 45 dias limpo. Em vez de se desesperar, respirou fundo, abriu o Recomeça, registrou o aprendizado no Diário e recomeçou o contador. Hoje está há mais de um ano sem apostar.",
            conclusion = "O que define sua vitória não é nunca tropeçar, mas o quão rápido você se levanta.",
            recommendedAction = "Se tiver tropeçado, utilize o botão de recomeço sem medo: suas conquistas permanecem com você."
        ),
        EducationalArticle(
            id = "vida_3",
            category = "Novos Hábitos e Propósito",
            title = "Celebrando Cada Pequena Vitória Diária",
            readTimeMinutes = 3,
            summary = "O valor inestimável de mais um dia vencido com a cabeça erguida.",
            introduction = "Grandes jornadas de superação são feitas de um único dia repetido muitas vezes.",
            mainText = "Não se preocupe com o que vai acontecer daqui a cinco anos. O único dia sobre o qual você tem total controle é hoje. Diga a si mesmo todas as manhãs: 'Apenas por hoje, eu não vou apostar. Apenas por hoje, vou proteger meu futuro e minha paz'.",
            realExample = "Ao comemorar cada dia concluído com um check-in no aplicativo, Tatiana percebeu sua autoestima renascendo dia após dia.",
            conclusion = "Cada dia que você passa sem apostar é uma medalha silenciosa de vitória pessoal.",
            recommendedAction = "Faça seu check-in diário no Recomeça agora e sinta orgulho da sua caminhada."
        )
    )
}
