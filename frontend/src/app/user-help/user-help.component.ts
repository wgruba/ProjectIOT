import { Component } from '@angular/core';

@Component({
  selector: 'app-user-help',
  templateUrl: './user-help.component.html',
  styleUrls: ['./user-help.component.scss']
})
export class UserHelpComponent {
  
  public items = [
    { question: 'Jak mogę skorzystać z serwisu?', answer: 'Aby skorzystać z naszej platformy, wystarczy przejść na stronę główną i przeglądać sugerowane eventy. Możesz również skorzystać z funkcji wyszukiwania, aby znaleźć konkretne wydarzenia wedle własnych parametrów.' },
    { question: 'Czy korzystanie z serwisu jest płatne?', answer: 'Nie, nasza platforma jest całkowicie bezpłatna dla użytkowników. Naszym celem jest umożliwienie łatwego dostępu do informacji o eventach dla wszystkich.' },
    { question: 'Jakie rodzaje eventów są dostępne na stronie?', answer: 'Na naszej stronie znajdziesz informacje o różnorodnych wydarzeniach, takich jak koncerty, festiwale, imprezy, wystawy i wiele innych. Nowe kategorie mogą być dodane w każdym momencie przez administrację!' },
    { question: 'Czy mogę dodawać własne wydarzenia do platformy?', answer: 'Tak, każdy zalogowany użytkownik może dodać własne wydarzenie do naszej platformy. Wystarczy przejść do zakładki "Dodaj wydarzenie" i wypełnić formularz. Po weryfikacji przez administrację, wydarzenie zostanie dodane do bazy danych.' },
    { question: 'Jakie informacje znajdę na stronie każdego wydarzenia?', answer: 'Na stronie każdego wydarzenia znajdziesz informacje o jego nazwie, kategorii, lokalizacji, dacie, godzinie, oraz link do strony wydarzenia, a także wszelkie inne informacje, które organizator doda w opisie.' },
    
  ]
  
  public selectedQuestion: string | null = null;

  public selectQuestion(question: string): void {
    this.selectedQuestion = this.selectedQuestion === question ? null : question;
  }

}

