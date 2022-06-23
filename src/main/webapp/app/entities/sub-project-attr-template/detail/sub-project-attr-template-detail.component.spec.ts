import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubProjectAttrTemplateDetailComponent } from './sub-project-attr-template-detail.component';

describe('SubProjectAttrTemplate Management Detail Component', () => {
  let comp: SubProjectAttrTemplateDetailComponent;
  let fixture: ComponentFixture<SubProjectAttrTemplateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SubProjectAttrTemplateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ subProjectAttrTemplate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SubProjectAttrTemplateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SubProjectAttrTemplateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load subProjectAttrTemplate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.subProjectAttrTemplate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
