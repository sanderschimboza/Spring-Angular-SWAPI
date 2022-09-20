import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

export class Swapi {
  constructor(
    public people: People,
  ) {
  }
}

export class People {
  constructor(
    public next: String,
    public results: Results[]
  ) {
  }
}

export class Results {
  constructor(
    public name: string,
    public height: string,
    public mass: string,
    public gender: string,
    public homeworld: string,
  ) {
  }
}


@Component({
  selector: 'app-people',
  templateUrl: './people.component.html',
  styleUrls: ['./people.component.css']
})
export class PeopleComponent implements OnInit {
  public people: Swapi;
  private SERVER_URL: string='http://localhost:9000/api/v1/swapi';

  constructor(
    private httpClient: HttpClient,
    private modalService: NgbModal
  ) {
  }

  ngOnInit(): void {
    this._getFriends()
  }
  _getFriends() {
    this.httpClient.post<Swapi>(this.SERVER_URL, "{\n" +
      "  people {\n" +
      "    next\n" +
      "    results {\n" +
      "      name\n" +
      "      height\n" +
      "      mass\n" +
      "      gender\n" +
      "      homeworld\n" +
      "    }\n" +
      "  }\n" +
      "}").subscribe(
      response=> {
        console.log(response);
        this.people=response;
      }
    );
  }

  openDetails(targetModal: any, person: Results) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'lg'
    });
    document.getElementById('name')?.setAttribute('value', person.name);
    document.getElementById('gender')?.setAttribute('value', person.gender);
    document.getElementById('height')?.setAttribute('value', person.height);
    document.getElementById('mass')?.setAttribute('value', person.mass);
    document.getElementById('homeworld')?.setAttribute('value', person.homeworld);
  }

}
