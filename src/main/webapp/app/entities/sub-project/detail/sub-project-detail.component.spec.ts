import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubProjectDetailComponent } from './sub-project-detail.component';

describe('SubProject Management Detail Component', () => {
  let comp: SubProjectDetailComponent;
  let fixture: ComponentFixture<SubProjectDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SubProjectDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ subProject: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SubProjectDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SubProjectDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load subProject on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.subProject).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
