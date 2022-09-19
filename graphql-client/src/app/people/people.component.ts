import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';

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
    public name: String,
    public height: String,
    public mass: String,
    public gender: String,
    public homeworld: String,
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
  closeResult: String;

  constructor(
    private httpClient: HttpClient,
    private modalService: NgbModal
  ) {
  }

  ngOnInit(): void {
    this._getFriends()
  }
  _getFriends() {
    this.httpClient.post<Swapi>("http://localhost:9000/data", "{\n" +
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
    // @ts-ignore
    document.getElementById('name').setAttribute('value', person.name);
    // @ts-ignore
    document.getElementById('gender').setAttribute('value', person.gender);
    // @ts-ignore
    document.getElementById('height').setAttribute('value', person.height);
    // @ts-ignore
    document.getElementById('mass').setAttribute('value', person.mass);
    // @ts-ignore
    document.getElementById('homeworld').setAttribute('value', person.homeworld);
  }



}
