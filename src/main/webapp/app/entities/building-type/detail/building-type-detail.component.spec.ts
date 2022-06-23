import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BuildingTypeDetailComponent } from './building-type-detail.component';

describe('BuildingType Management Detail Component', () => {
  let comp: BuildingTypeDetailComponent;
  let fixture: ComponentFixture<BuildingTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BuildingTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ buildingType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BuildingTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BuildingTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load buildingType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.buildingType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
